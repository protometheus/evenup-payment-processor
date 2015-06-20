package com.evenup.payment.processor

//import com.yammer.dropwizard.testing.ResourceTest

import io.dropwizard.testing.junit.ResourceTestRule

import org.junit.Rule

import spock.lang.Specification

abstract class ResourceSpecification extends Specification {

    abstract Object getResource()

    @Rule ResourceTestRule resources = ResourceTestRule.builder()
        .addResource(getResource())
        .build();

}
