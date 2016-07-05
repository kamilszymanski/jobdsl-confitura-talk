/**
 * To run this example you have to change `repository` to your git repository containing
 * gradle project that defines build, publish and acceptanceTests tasks
 */

import pl.confitura.jobdsl.pipeline.customizable.CustomizableDeliveryPipeline
import pl.confitura.jobdsl.pipeline.customizable.step.*

def projectProperties = [repository: 'file:///home/kamils/confitura/projects/hello-confitura',
                         scmPoll: '@daily']
def pipelineSteps = [BuildAndPublish, DeployOnTest, AcceptanceTests, DeployOnStage, DeployOnProduction]

new CustomizableDeliveryPipeline(this, 'hello-confitura', projectProperties).build(pipelineSteps)
