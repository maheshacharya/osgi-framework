package com.achxis;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Hello world!
 */
public class App {

    private Framework framework;
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws BundleException, IOException {

        App app = new App();
        app.startFramework();

    }


    public void startFramework() throws BundleException, IOException {
        FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
        Map<String, String> config = new HashMap<String, String>();
        framework = frameworkFactory.newFramework(config);
        logger.info("\n********************\n\n\n");
        logger.info("Bundle ID: " + framework.getBundleId());
        logger.info("Symbolic Name: " + framework.getSymbolicName());
        framework.start();
        logger.info("\n********************\n\n\n" +
                "OSGi Framework Started\n\n\nPress \"ctrl + c\" to terminate");
        logger.info(new File(System.getProperty("user.dir")).getCanonicalPath());
        logger.info("adding bundles");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addBundles();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopBundles();

    }

    private static String jarPath = System.getProperty("user.home")+"/temp/test-bundle-1.0-SNAPSHOT.jar";

    public void addBundles() throws BundleException {
        BundleContext context = framework.getBundleContext();
        List<Bundle> installedBundles = new LinkedList<Bundle>();
        installedBundles.add(context.installBundle("file:" + jarPath));

        for (Bundle bundle : installedBundles) {
            bundle.start();
        }
        logger.info("Bundles started");
    }

    public void stopBundles() throws BundleException {
        BundleContext context = framework.getBundleContext();
        List<Bundle> installedBundles = new LinkedList<Bundle>();
        // installedBundles.add(context.installBundle("file:/Users/achxis-macbook/projects/osgi/achxis-osgi/test-bundle/target/test-bundle-1.0-SNAPSHOT.jar"));
        installedBundles.add(context.installBundle("file:" + jarPath));

        for (Bundle bundle : installedBundles) {
            bundle.stop();
        }

    }


    public Framework getFramework() {
        return framework;
    }

    public void setFramework(Framework framework) {
        this.framework = framework;
    }
}
