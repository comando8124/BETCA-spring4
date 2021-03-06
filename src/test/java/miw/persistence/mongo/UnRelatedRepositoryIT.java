package miw.persistence.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import miw.persistence.mongo.documents.UnRelatedDocument;
import miw.persistence.mongo.repositories.UnRelatedRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnRelatedRepositoryIT {

    @Autowired
    private UnRelatedRepository unRelatedRepository;

    @Before
    public void populate() {
        this.unRelatedRepository.deleteAll();
        for (int i = 0; i < 5; i++) {
            this.unRelatedRepository.save(new UnRelatedDocument("nick" + i));
        }
    }

    @Test
    public void test() {
        assertTrue(unRelatedRepository.count() > 0);
    }

    @Test
    public void testFindByNickIgnoreCase() {
        assertNotNull(unRelatedRepository.findByNickIgnoreCase("NICK1"));
    }

    @Test
    public void testFindFirst3ByNickStartingWith() {
        assertEquals(0, unRelatedRepository.findFirst3ByNickStartingWith("no").size());
        assertEquals(3, unRelatedRepository.findFirst3ByNickStartingWith("ni").size());
    }

    @Test
    public void testFindByNickOrLargeOrderByIdDesc() {
        assertTrue(unRelatedRepository.findByNickOrLargeOrderByLogerDesc("NoNick", "Large...").get(0).getLoger() >= unRelatedRepository
                .findByNickOrLargeOrderByLogerDesc("NoNick", "Large...").get(1).getLoger());
        assertEquals(5, unRelatedRepository.findByNickOrLargeOrderByLogerDesc("NoNick", "Large...").size());
    }

    @Test
    public void testFindByIdGreaterThan() {
        assertEquals(2, unRelatedRepository.findByIntegerGreaterThan(0, new PageRequest(0, 2)).size());
        assertEquals(2, unRelatedRepository.findByIntegerGreaterThan(0, new PageRequest(1, 3)).size());
    }

    @Test
    public void testFindByNickIn() {
        assertEquals(2, unRelatedRepository.findByNickIn(Arrays.asList(new String[] {"nick1", "nick2"})).size());
    }

    // BORRADO
    @Test
    public void testDeleteByNick() {
        assertNotNull(unRelatedRepository.findByNick("nick0"));
        unRelatedRepository.deleteByNick("nick0");
        assertNull(unRelatedRepository.findByNick("nick0"));
    }

    // Query
    @Test
    public void testFindByNick() {
        assertNotNull(unRelatedRepository.findByNick("nick0"));
    }

    @Test
    public void testFindIdAndBornDateAndLargeByNick() {
        assertNotNull(unRelatedRepository.findIdAndBornDateAndLargeByNick("nick0").getId());
        assertNotNull(unRelatedRepository.findIdAndBornDateAndLargeByNick("nick0").getBornDate());
        assertNotNull(unRelatedRepository.findIdAndBornDateAndLargeByNick("nick0").getLarge());
    }

    @Test
    public void testFindBornDateByNick() {
        assertNotNull(unRelatedRepository.findBornDateByNick("nick0").getBornDate());
    }

    @Test
    public void testFindByNickAndLarge() {
        assertNotNull(unRelatedRepository.findByNickAndLarge("nick0", "Large..."));
        assertNull(unRelatedRepository.findByNickAndLarge("nick0", "NOT"));
    }

}
