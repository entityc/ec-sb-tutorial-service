# ec-sb-tutorial-service

## Introduction

This repository contains the example Tutorial Microservice that you get when you complete the [Tutorial Microservice Tutorial](https://github.com/entityc/ec-tutorials/tree/main/TutorialMicroservice). It is provided here in case you just want to play with it without taking the tutorial. After you build and run it you can always take the tutorial or refer to parts of it to understand more.

The microservice is based on [Spring Boot](https://spring.io/projects/spring-boot) (thus the `-sb-` in the repository name).

## Getting Started

One of the easiest ways to run this microservice is on your local machine. You will need to make sure you have the following installed.

### Java

The microservice itself is written in Java so it can run as long as you have Java installed.

### PostgreSQL

The microservice needs to connect to a Postgres database so you will need to install [PostgreSQL](https://www.postgresql.org) v11 or higher if it is not already installed. Alternately you can also connect to a remote Postgres database if that is available to you.

If you do install PostgreSQL, you'll need to make sure that `psql` is in your search path in your shell startup by adding the PostgreSQL `bin` directory to your `PATH`. For example, on the Mac for version 14, it might look like:

    export PATH=$PATH:/Library/PostgreSQL/14/bin

By default, this microservice is configured to connect to a database by the name of `ectutorialdb` and a user named `postgres` with a password of `postgres`. You are free to use whichever configuration you prefer, just make sure to edit the [application.yml](src/main/resources/application.yml) file, specifically the lines:

```
  datasource:
    url: 'jdbc:postgresql://localhost:5432/ectutorialdb'
    username: 'postgres'
    password: 'postgres'
```

### Maven

[Maven](https://maven.apache.org/install.html) is used to compile and run the microservice so you will need to have it installed as well.

### Running the microservice

Once you have the above in place, you are ready to install and run this microservice as follows:

1. Clone this repository to you local machine (or download the ZIP and unzip). This should create a directory called `ec-sb-tutorial-service`.
2. Open a bash-like shell and change to the `ec-sb-tutorial-service` directory.
3. From the command line execute:

```shell
./run.sh
```

This will invoke maven to compile and then run the microservice. 

A lot of log messages will come out but if successful ultimately you should see something like:
```
2022-02-05 09:22:46.140  INFO 16503 --- [           main] o.e.t.ECTutorialServiceApplication       : Started ECTutorialServiceApplication in 7.703 seconds (JVM running for 8.064)
```

If there are issues connecting to your database, there should be appropriate error messages. If this happens, just confirm that what you have configured in the `application.yml` file is correct.

### Using the Tutorial Microservice

The microservice has a REST API but also comes with a web interface. Start by opening a web browser to the following URL:

```
http:/localhost:8080
```

This will bring you to the login screen. Since this is the first time to use the site we will need to register. Under the **Log In** button there is a link: **Sign Up here**. Click that and it will take you to the signup screen. There you can sign up with your name, email address and password.

After signing up, go ahead and shutdown the microservice (press ctrl-c in the shell where you invoked `run.sh` above). We need to make this first user an admin but since there are no admins available yet to do this, we will have to go into the database directly and upgrade us that way (once we are an admin we will be able to change permissions of future users via the web admin console).

In a shell use `psql` to connect to your database (if you configured it differently than the default, use those settings).

```shell
psql -d ectutorialdb -U postgres
```

The first thing we need to do is to find out your user ID. This can be done with a select:

```postgresql
select user_id from platform_user;
```

This will output a UUID that is your user ID. Now we will perform an insert into the `platform_user_roles` table to give us both **instructor** and **admin** permissions (substitute `your-uuid-from-above` with your user ID that we got from the previous `select` statement):

```postgresql
insert into platform_user_roles (user_id, value) values ('your-uuid-from-above', 1);
insert into platform_user_roles (user_id, value) values ('your-uuid-from-above', 2);
```

This should do it, exit `psql`.

Now we should start up the microservice again by running:

```shell
./run.sh
```

We are now ready to access the **admin** side of the site. In your web browser, set your URL to this:

```
http://localhost:8080/admin
```

The screen should show two buttons: **Tutorials** and **Users**. Click on the **Tutorials** button. Of course there won't be any tutorials but there should be a **New Tutorial** button. Press this button and then enter a **title** and unique **identifier** for the tutorial, then press **Create**. Now you have your first tutorial. You can now edit it to fill in modules, sessions, exercises and steps. When you have a bit content entered, jump over to the **user** side of the site to see how it looks by simply setting the browser URL to `http://localhost:8080`.

## Going Further

This microservice was built with the [Entity Compiler](http://github.com/entityc/entity-compiler). The source files used to synthesize much of its code is located under the [`src/ec`](src/ec) directory of this repository. To understand how the compiler works its best to take the [Entity Compiler Tutorial](https://github.com/entityc/ec-tutorials/tree/main/EntityCompiler). To understand how this microservice in particular is built you can take the [Tutorial Microservice Tutorial](https://github.com/entityc/ec-tutorials/tree/main/TutorialMicroservice). This will give you the information you need to not only make changes or enhancements to this microservice but also the ability to create other microservices.

This microservice is also built using code templates from the [ec-std-lib](http://github.com/entityc/ec-std-lib) and [ec-springboot-lib](http://github.com/entityc/ec-springboot-lib) repositories. Although they have enough features to generate this microservice, they likely will not have enough to build all features you might require in your own microservice. You can build new features in your own microservice (with your own templates) but you are encouraged to contribute new features to one of these repositories or some other open source repository.

## Contributing

This repository is read-only since it represents the result of the completion of a tutorial ([Tutorial Microservice Tutorial](https://github.com/entityc/ec-tutorials/tree/main/TutorialMicroservice)). If any changes are made to the tutorial, this microservice may change as a result. Contributions to the Entity Compiler and its code libraries are always welcome, please see those repositories for instructions on how to contribute.

## Licensing

All projects of the EntityC Organization are under the BSD 3-clause License.

See [LICENSE.md](LICENSE.md) for details.
