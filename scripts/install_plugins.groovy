List plugins = [
        'git',
        'job-dsl',
        'gradle',
        'parameterized-trigger',
        'build-pipeline-plugin',
        'delivery-pipeline-plugin'
]

def pluginManager = PluginManager.createDefault(Jenkins.instance)
pluginManager.install(plugins, true)
