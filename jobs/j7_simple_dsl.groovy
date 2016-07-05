/**
 * To run this example you have to change `repository` to your git repository containing
 * gradle project that defines build, publish and acceptanceTests tasks
 */

import pl.confitura.jobdsl.pipeline.simple.SimpleDeliveryPipeline

def projectProperties = [projectName: 'hello-confitura',
                         repository: 'file:///home/kamils/confitura/projects/hello-confitura',
                         scmPoll: '@daily']

new SimpleDeliveryPipeline(projectProperties).build(this)
