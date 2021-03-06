package miw.persistence.jpa.daos;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import miw.persistence.jpa.daos.UnidirectionalOneToOneEmbeddedDao;
import miw.persistence.jpa.entities.EmbeddableEntity;
import miw.persistence.jpa.entities.UnidirectionalOneToOneEmbeddedEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnidirectionalOneToOneEmbeddedDaoIT {

    @Autowired
    private UnidirectionalOneToOneEmbeddedDao unidirectionalOneToOneEmbeddedDao;

    private EmbeddableEntity embeddable;

    private UnidirectionalOneToOneEmbeddedEntity entity;

    @Before
    public void before() {
        this.embeddable = new EmbeddableEntity(666, "daemon");
        this.entity = new UnidirectionalOneToOneEmbeddedEntity("Nick", embeddable);
    }

    @Test
    public void testFindOne() {
        unidirectionalOneToOneEmbeddedDao.save(entity);
        assertEquals("Nick", unidirectionalOneToOneEmbeddedDao.findOne(entity.getId()).getNick());
        assertEquals("daemon", unidirectionalOneToOneEmbeddedDao.findOne(entity.getId()).getEmbeddableEntity().getValue());
    }

    @After
    public void delete() {
        unidirectionalOneToOneEmbeddedDao.delete(this.entity);
    }

}
