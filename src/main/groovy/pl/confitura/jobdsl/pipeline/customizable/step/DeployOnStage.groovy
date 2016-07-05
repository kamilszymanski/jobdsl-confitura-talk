package pl.confitura.jobdsl.pipeline.customizable.step

import static pl.confitura.jobdsl.pipeline.customizable.step.publisher.DownstreamTriggerContext.manuallyTriggerDownstreamJob

class DeployOnStage extends PipelineStep {

    DeployOnStage(String projectName) {
        super("${projectName}-deploy-on-stage")
    }

    @Override
    void build() {
        dslFactory.job(jobName) {
            deliveryPipelineConfiguration 'stage'
            steps {
                shell 'echo "deploying version $version on stage"'
            }
            publishers {
                manuallyTriggerDownstreamJob(delegate, downstreamJob)
            }
        }
    }

}
