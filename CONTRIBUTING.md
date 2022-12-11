# Contributing to CONTRIBUTING.md

First off, thanks for taking the time to contribute! 😍

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [I Have a Question](#i-have-a-question)
- [Styleguides](#styleguides)
- [Commit Messages](#commit-messages)


## Code of Conduct

When interacting with members of this project you are expected
to follow standards of conduct such as:
- Be nice
- Be respectful
- Be helpful
- Be patient

If ignored we will do the same to you (ignore you).


## I Have a Question

> First, read the available [Documentation](https://pns-si3-projects.github.io/projet2-ps5-22-23-takenoko-2023-c/).

If you have a question, follow these steps:
- Open an issue with the label `question`.
- Describe your question as clearly as possible and provide context.
- Wait patiently for a response (we'll do our best).


## Styleguides

We are happy to hear that you want to contribute to this project. But we ask you to follow these styleguides to have an organised project.

### Issues

When working on your feature, please open an issue beforehand and connect your issue to its parent user story issue, from there create you `feat-<name>` branch.

### Branches

Our branches follow the following naming convention:
- `feat-<name_of_the_issue>` for new features
- `us-<name_of_the_issue>` for user stories

You should be coding in a `feat` branch. When done with your task open a pull request to merge your branch into the parent user story.

When a user story is done, it is merged into the `develop` branch. We ask you kindly to help us by reviewing the pull requests of your teammates.

For each deployement a final pull request will be opened to merge the `develop` branch into the default `master` branch.

### Commit Messages

We use standard commits messages to keep track of the changes made in the project. We use the following convention:
- `feat: <description>` for new features
- `fix: <description>` for bug fixes
- `test: <description>` for tests
- `refactor: <description>` for refactoring
- `docs: <description>` for documentation

Additionally, you can specify the scope of the commit like this:

`feat(<scope>): <description>`
