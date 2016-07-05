/**
 * To run this example you have to change git repository url to your repository containing
 * gradle project that defines build, publish and acceptanceTests tasks
 */

job('01-build') {
    scm {
        git 'file:///home/kamils/confitura/projects/hello-confitura'
    }
    triggers {
        scm '@daily'
    }
    steps {
        gradle 'build publish'
    }
    publishers {
        downstreamParameterized {
            trigger('02-deploy-on-test') {
                condition 'SUCCESS'
                parameters {
                    predefinedProp('version', '$GIT_COMMIT')
                }
            }
        }
    }
}

job('02-deploy-on-test') {
    steps {
        shell 'echo "deploying hello-confitura v. $version on test"'
    }
    publishers {
        downstreamParameterized {
            trigger('03-acceptance-tests') {
                condition 'SUCCESS'
                parameters {
                    currentBuild()
                }
            }
        }
    }
}

job('03-acceptance-tests') {
    scm {
        git('file:///home/kamils/confitura/projects/hello-confitura', '$version')
    }
    steps {
        gradle 'acceptanceTests'
    }
    publishers {
        downstreamParameterized {
            trigger('04-deploy-on-production') {
                condition 'SUCCESS'
                parameters {
                    currentBuild()
                }
            }
        }
    }
}

job('04-deploy-on-production') {
    steps {
        shell 'echo "deploying hello-confitura v. $version on production"'
    }
}

deliveryPipelineView('hello-confitura-customizable-delivery-view') {
    allowPipelineStart()
    showChangeLog()
    pipelines {
        component('Hello Confitura', '01-build')
    }
}