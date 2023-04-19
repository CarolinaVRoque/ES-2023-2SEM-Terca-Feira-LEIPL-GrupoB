package org.es.leipl.tercafeira.grupob.tools;

import org.junit.runner.Result;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

public class ImportFilesTestRunner {

    public static void main(String[] args){
        Result result = JUnitCore.runClasses(ImportFilesTestSuite.class);
        for (Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println("All tests were successful: " + result.wasSuccessful());
    }
}
