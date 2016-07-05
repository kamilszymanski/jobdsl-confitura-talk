import pl.confitura.jobdsl.pipeline.simple.SimpleDeliveryPipeline

def projectProperties = [projectName: 'hello-confitura',
                         repository: 'file:///home/kamils/confitura/projects/hello-confitura',
                         scmPoll: '@daily']

new SimpleDeliveryPipeline(projectProperties).build(this)
