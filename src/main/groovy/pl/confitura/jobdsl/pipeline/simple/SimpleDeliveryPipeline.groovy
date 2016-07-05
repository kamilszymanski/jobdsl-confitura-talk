package pl.confitura.jobdsl.pipeline.simple

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.View

class SimpleDeliveryPipeline {

    private static final String SUCCESSFUL_BUILD = 'SUCCESS'

    DslFactory dslFactory
    String projectName
    String scmPoll
    String repository

    void build(DslFactory dslFactory) {
        this.dslFactory = dslFactory
        assemblePipeline()
        createPipelineView()
    }

    private void assemblePipeline() {
        createBuildJob()
        createDeployOnTestJob()
        createAcceptanceTestsJob()
        createDeployOnProductionJob()
    }

    private Job createBuildJob() {
        dslFactory.job("${projectName}-build") {
            scm {
                git repository
            }
            triggers {
                scm scmPoll
            }
            steps {
                gradle 'build publish'
            }
            publishers {
                downstreamParameterized {
                    trigger("${projectName}-deploy-on-test") {
                        condition SUCCESSFUL_BUILD
                        parameters {
                            predefinedProp('version', '$GIT_COMMIT')
                        }
                    }
                }
            }
        }
    }

    private Job createDeployOnTestJob() {
        dslFactory.job("${projectName}-deploy-on-test") {
            steps {
                shell 'echo "deploying version $version on test"'
            }
            publishers {
                downstreamParameterized {
                    trigger("${projectName}-acceptance-tests") {
                        condition SUCCESSFUL_BUILD
                        parameters {
                            currentBuild()
                        }
                    }
                }
            }
        }
    }

    private Job createAcceptanceTestsJob() {
        dslFactory.job("${projectName}-acceptance-tests") {
            scm {
                git(repository, '$version')
            }
            steps {
                gradle 'acceptanceTests'
            }
            publishers {
                downstreamParameterized {
                    trigger("${projectName}-deploy-on-production") {
                        condition SUCCESSFUL_BUILD
                        parameters {
                            currentBuild()
                        }
                    }
                }
            }
        }
    }

    private Job createDeployOnProductionJob() {
        dslFactory.job("${projectName}-deploy-on-production") {
            steps {
                shell 'echo "deploying version $version on production"'
            }
        }
    }

    private View createPipelineView() {
        dslFactory.deliveryPipelineView("${projectName}-simple-delivery-view") {
            allowPipelineStart()
            showChangeLog()
            pipelines {
                component(projectName, "${projectName}-build")
            }
        }
    }

}
