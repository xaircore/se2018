package com.xairlab.otus.annotation.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.xairlab.otus.annotation.api.After;
import com.xairlab.otus.annotation.api.Before;
import com.xairlab.otus.annotation.api.Test;
import com.xairlab.otus.annotation.api.TestStatus;
import com.xairlab.otus.annotation.runner.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Suite {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    private final String name;
    private Method before;
    private Method after;
    private List<Case> cases;
    private int success = 0;
    private int failed = 0;

    public Suite(String name) {
        this.name = name;
        this.cases = getCases();
    }

    private List<Case> getCases() {
        ArrayList<Method> methods = new ArrayList<>();
        ArrayList<Case> cases = new ArrayList<>();
        try {
            Class suite = Class.forName(this.name);
            Constructor factory = suite.getConstructor();
            for (Method testMethod : suite.getMethods()) {
                if (testMethod.isAnnotationPresent(Test.class)) {
                    methods.add(testMethod);
                } else if (testMethod.isAnnotationPresent(Before.class)) {
                    before = testMethod;
                } else if (testMethod.isAnnotationPresent(After.class)) {
                    after = testMethod;
                }
            }
            for (Method test : methods) {
                cases.add(new Case(factory, before, after, test));
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            logger.error("Can't create Cases", e.getCause());
        }
        return cases;
    }

    public void exec() {
        logger.info("Run suite: " + name);
        for (Case test : cases) {
            TestStatus status = test.exec();
            switch (status) {
                case SUCCESS:
                    success++;
                    break;
                case FAILED:
                    failed++;
                    break;
            }
        }
    }

    public int getTotalCount() {
        return cases.size();
    }

    public int getSuccessCount() {
        return success;
    }

    public int getFailedCount() {
        return failed;
    }
}
