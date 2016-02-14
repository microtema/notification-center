package it.de.seven.fate.message.dao;

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
                // .addPackages(true, AppSettingStringBO.class.getPackage())

                .addClasses(classes)
                .addClasses(
                )
                //   .addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3").resolveAsFiles())
                .addAsResource("xml/uxb-app-entity.xml", "xml/uxb-app-entity.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-beans.xml")
                .addAsWebInfResource("jbossas-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebResource("webapp/index.jsp", "index.jsp")
                .addAsWebInfResource("WEB-INF/web.env-entry.xml", "web.xml");

        return war;
    }
}
