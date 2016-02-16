package it.de.seven.fate;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

/**
 * Created by Mario on 14.02.2016.
 */
public class ArchiveDeployment {

    public static WebArchive createDeployment(Class... classes) {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "de.seven.fate.message")
                .addPackages(true, "de.seven.fate.person")
                .addPackages(true, "de.seven.fate.dao")
                .addPackages(true, "de.seven.fate.util")
                .addPackages(true, "de.seven.fate.builder")
                .addPackages(true, "de.seven.fate.converter")
                .addPackages(true, "de.seven.fate.xml")
                .addClasses(classes)
                .addClasses()
                .addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3").resolveAsFiles())
                .addAsLibraries(resolver.artifact("commons-io:commons-io").resolveAsFiles())
                .addAsLibraries(resolver.artifact("commons-beanutils:commons-beanutils").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.jboss.resteasy:resteasy-jackson-provider").resolveAsFiles())

                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("message-schema.xsd", "classes/message-schema.xsd")
                .addAsWebInfResource("test-beans.xml")
                .addAsWebInfResource("jbossas-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("WEB-INF/web.env-entry.xml", "web.xml");

        return war;
    }
}
