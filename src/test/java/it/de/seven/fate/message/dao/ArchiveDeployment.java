package it.de.seven.fate.message.dao;

import de.seven.fate.dao.GenericEntityDAO;
import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;
import de.seven.fate.person.enums.MessageType;
import de.seven.fate.person.model.Person;
import de.seven.fate.util.ClassUtil;
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
                .addClasses(classes)
                .addClasses(
                )
                .addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3").resolveAsFiles())
                .addAsLibraries(resolver.artifact("commons-io:commons-io").resolveAsFiles())
                .addAsLibraries(resolver.artifact("commons-beanutils:commons-beanutils").resolveAsFiles())
                .addAsLibraries(resolver.artifact("org.jboss.resteasy:resteasy-jackson-provider").resolveAsFiles())

                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-beans.xml")
                .addAsWebInfResource("jbossas-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("WEB-INF/web.env-entry.xml", "web.xml");

        return war;
    }
}
