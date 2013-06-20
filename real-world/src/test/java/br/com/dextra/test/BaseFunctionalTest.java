package br.com.dextra.test;

import org.junit.BeforeClass;

import com.googlecode.mycontainer.kernel.boot.ContainerBuilder;
import com.googlecode.mycontainer.web.ContextWebServer;
import com.googlecode.mycontainer.web.jetty.JettyServerDeployer;

public abstract class BaseFunctionalTest {

	private static boolean mycontainerStarted = false;

	@BeforeClass
	public static void startContainers() throws Exception {
		if (!mycontainerStarted) {
			System.setProperty("java.naming.factory.initial",
					"com.googlecode.mycontainer.kernel.naming.MyContainerContextFactory");

			ContainerBuilder builder = new ContainerBuilder();
			builder.deployVMShutdownHook();
			JettyServerDeployer server = builder.createDeployer(JettyServerDeployer.class);

			server.setName("WebServer");
			server.bindPort(8080);

			ContextWebServer web = server.createContextWebServer();
			web.setContext("/");
			web.setResources("src/main/webapp");

			server.deploy();

			mycontainerStarted = true;
		}
	}
}
