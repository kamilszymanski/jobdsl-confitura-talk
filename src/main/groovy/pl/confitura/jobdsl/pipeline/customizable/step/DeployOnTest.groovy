package pl.confitura.jobdsl.pipeline.customizable.step

import static pl.confitura.jobdsl.pipeline.customizable.step.publisher.DownstreamTriggerContext.triggerDownstreamJob

class DeployOnTest extends PipelineStep {

    DeployOnTest(String projectName) {
        super( "${projectName}-deploy-on-test")
    }

    @Override
    void build() {
        dslFactory.job(jobName) {
            deliveryPipelineConfiguration 'test'
            steps {
                shell 'echo "deploying version $version on test"'
            }
            publishers {
                triggerDownstreamJob(delegate, downstreamJob)
            }
        }
    }

}
