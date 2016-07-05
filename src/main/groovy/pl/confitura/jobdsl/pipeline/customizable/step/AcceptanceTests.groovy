package pl.confitura.jobdsl.pipeline.customizable.step

import static pl.confitura.jobdsl.pipeline.customizable.step.publisher.DownstreamTriggerContext.triggerDownstreamJob

class AcceptanceTests extends PipelineStep {

    AcceptanceTests(String projectName) {
        super("${projectName}-acceptance-tests")
    }

    @Override
    void build() {
        dslFactory.job(jobName) {
            deliveryPipelineConfiguration 'test'
            scm {
                git(context.repository, '$version')
            }
            steps {
                gradle 'acceptanceTests'
            }
            publishers {
                triggerDownstreamJob(delegate, downstreamJob)
            }
        }
    }

}
