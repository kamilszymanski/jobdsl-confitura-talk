import pl.confitura.jobdsl.pipeline.customizable.step.*
import pl.confitura.jobdsl.pipeline.parallelizable.ParallelizableDeliveryPipeline

def projectProperties = [repository: 'file:///home/kamils/confitura/projects/hello-confitura',
                         scmPoll: '@daily']

new ParallelizableDeliveryPipeline('hello-confitura', projectProperties)
    .withSteps(
        BuildAndPublish
            .then(DeployOnTest)
            .then(
                parallel(AcceptanceTests, StaticCodeAnalysis)
            ).then(DeployOnStage)
            .then(DeployOnProduction)
    ).build(this)