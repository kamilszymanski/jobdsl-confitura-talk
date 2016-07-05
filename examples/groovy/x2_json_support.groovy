import groovy.json.JsonSlurper

String metadata = """
                    {
                        "greeting": "Hello Confitura!",
                        "motd": {
                            "short": "Automate!",
                            "full": "Automate all the things!"
                        }
                    }
                    """

def json = new JsonSlurper().parseText(metadata)
println json.motd.full