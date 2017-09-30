# Hydra Enterprise Edition (HEE)

## Introduction

Hydra Enterprise Edition (or HEE™ for short) is a project to improve the architecture, design and
structure of the Hydra application for Android.

Note that when we say design, we mean it in terms of code, not user interface. This project is
entirely transparent for end users (or should be).

## Status

A lot of groundwork has been finished. At the moment, various functionality is being converted/rewritten into
the new architecture.

As such, this branch is not stable at all and might not even compile at times.

## Goal

The goal of the project is to re-architecture the app to use Clean Architecture. This guide does
not describe this architectural pattern, but more the implementation and details we have chosen.

Some good resources are:

- [The Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) on Uncle Bob's blog.
This is an explanation by the _inventor_ himself.
- A good series on applying it on Android by Five:
    * [Part 1](http://five.agency/android-architecture-part-1-every-new-beginning-is-hard/)
    * [Part 2](http://five.agency/android-architecture-part-2-clean-architecture/)
    * [Part 3](http://five.agency/android-architecture-part-3-applying-clean-architecture-android/) (particularly interesting)
    * [Part 4](http://five.agency/android-architecture-part-4-applying-clean-architecture-on-android-hands-on/)
    * [Part 5](http://five.agency/android-architecture-part-5-how-to-test-clean-architecture/)
    
## Scope

This effort will probably require a large part of the _logic_ of the app to be restructured, if not rewritten.

Because we already use partial things from this architectural design on the UI front, there will be very little
changes on the UI portion of the app.

## Motivation

The current Android does not have a _bad_ architecture. The UI is at least separate from network and database logic.
The problem lies in the fact that the logic is fairly spread out; parts are in the DAO's, parts are in the UI class.
This stems from the fact that the features of the app have grown organically. The original app did little more than
fetch things from a server and display them in the app.

Some portions of the app still do only that, which adds another challenge: we want the architecture to
be able to cleanly separate concerns, but at the same time not introduce unnecessary boiler plate for the
simple parts of the app.