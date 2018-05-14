# JHipster Online

JHipster Online is a Web application that allows to generate [JHipster applications](https://www.jhipster.tech/) 
without installing JHipster on your machine.

This is an Open Source project ([Apache 2 license](https://github.com/jhipster/jhipster-online/blob/master/LICENSE.txt))
that powers the [https://start.jhipster.tech/](https://start.jhipster.tech/) website.

You can use [https://start.jhipster.tech/](https://start.jhipster.tech/) for free, but if you find a bug or need a specific
feature, you are welcome to contribute to the project. You can also choose to clone or fork the project, and have your own version
that is hosted within your company.

## Help and contribution to the project

Please note that this project is part of the [JHipster organization](https://github.com/jhipster) and it follows the rules 
of the [JHipster project](https://github.com/jhipster/generator-jhipster).

### If you have an issue, a bug or a feature request

Please follow our [contribution guide](https://github.com/jhipster/jhipster-online/blob/master/CONTRIBUTING.md).

### If you have a question or need help

You should [post it on Stack Overflow using the "jhipster" tag](https://stackoverflow.com/questions/tagged/jhipster?sort=newest).

### Code of conduct

We have the same code of conduct as the main JHipster project:
[JHipster code of conduct](https://github.com/jhipster/jhipster-online/blob/master/CODE_OF_CONDUCT.md).

## Development

JHipster Online is JHipster application, so we encourage you to look at [the JHipster website](https://www.jhipster.tech/)
to understand better how you can work with the project.

## Installation

As this is a JHipster project, you should follow the official JHipster documentation, but if here is a quickstart guide.

- Install and run the front-end:


    yarn install && yarn start


- Run the database:


    docker-compose -f src/main/docker/mysql.yml up -d


- Run the back-end:


    ./mvnw

## Building for production

To generate a production build, like any normal JHipster application, please run:

    ./mvnw -Pprod clean package

## Using Docker
You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw package -Pprod dockerfile:build

Then run:

    docker-compose -f src/main/docker/app.yml up -d
