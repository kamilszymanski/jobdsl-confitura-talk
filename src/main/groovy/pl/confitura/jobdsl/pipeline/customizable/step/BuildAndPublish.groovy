package pl.confitura.jobdsl.pipeline.customizable.step

import static pl.confitura.jobdsl.pipeline.customizable.step.publisher.DownstreamTriggerContext.triggerDownstreamJob

class BuildAndPublish extends PipelineStep {

    BuildAndPublish(String projectName) {
        super("${projectName}-build")
    }

    @Override
    void build() {
        dslFactory.job(jobName) {
            deliveryPipelineConfiguration 'build'
            scm {
                git context.repository
            }
            triggers {
                scm context.scmPoll
            }
            steps {
                gradle 'build publish'
            }
            publishers {
                triggerDownstreamJob(delegate, downstreamJob, [version: '$GIT_COMMIT'])
            }
        }
    }

}
