[
    [greet: 'Confitura', trigger: '0 9 2 7 0'],
    [greet: 'World', trigger: '@daily'],
    [greet: 'Poland', trigger: '@annually']
].each { Map config ->
    job("hello-${config.greet.toLowerCase()}") {
        triggers {
            cron(config.trigger)
        }
        steps {
            shell "echo \"Hello ${config.greet}!\""
        }
    }
}

listView('hello-projects') {
    jobs {
        regex '^hello-.*'
    }
    columns {
        status()
        name()
        buildButton()
    }
}