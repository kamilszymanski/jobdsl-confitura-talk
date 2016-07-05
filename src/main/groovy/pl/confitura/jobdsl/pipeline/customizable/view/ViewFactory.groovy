package pl.confitura.jobdsl.pipeline.customizable.view

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.View

class ViewFactory {

    static View createDeliveryPipelineView(DslFactory dslFactory, String projectName, String initialJob) {
        dslFactory.deliveryPipelineView("${projectName}-customizable-delivery-view") {
            allowPipelineStart()
            enableManualTriggers()
            showChangeLog()
            pipelines {
                component(projectName, initialJob)
            }
        }
    }

}
