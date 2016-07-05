package pl.confitura.jobdsl.pipeline.customizable.step

class DeployOnProduction extends PipelineStep {

    DeployOnProduction(String projectName) {
        super("${projectName}-deploy-on-production")
    }

    @Override
    void build() {
        dslFactory.job(jobName) {
            deliveryPipelineConfiguration 'production'
            steps {
                shell 'echo "deploying version $version on production"'
            }
        }
    }

}
