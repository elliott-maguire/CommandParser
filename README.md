# Command Parser

## Project Setup
The provided JAR will always be in `vendor/`, which will contain all versions.
It is your responsibility to, when a new version is added, update your configuration.

The configurations for IntelliJ have been added to the repository by default, but other
configurations can be added.

## Workflow
**Please review these before starting! These things are in place to make our process smoother and easier.**

Branches are for command groupings, commits are for requirements. E.g. branch name `stock-parser` with commits of
"add box parser (#28)" where 28 is the requirement number.

If there is a bug or something needs to be refactored, create an issue and then name the branch
according to that issue, e.g if I create an issue with the ID of 15, I'd name my branch `issue-15`.
Link PRs and issues together whenever possible.

Use comments on issues and PRs when appropriate - it can help keep communication
clear and organized.

All PRs targeting `main` must have at least 1 review before merging is possible. This will
protect us from merging breaking changes with the main branch, which can be a nightmare to
fix and work around.

When reviewing, watch out for such breaking changes. If a change is made to a fundamental mechanism to
support a hacky command implementation, we'll discuss and rethink it because that shouldn't be necessary, and if it is,
we should open an issue and address it in an independent PR. Core architecture changes should not be common.

**Use atomic commits.** Only one thing should be done in a given commit.
Do not simply `git add .` and `git commit -m "did stuff"` after doing a ton of work.
Change something small, commit it. Change another something small, commit it, etc.
PRs with fat commits should be rejected.

If you have questions, **ask**. Better to ask the question than do something wrong and end up with a messy codebase
and confused team.