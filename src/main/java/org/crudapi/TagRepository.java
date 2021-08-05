package org.crudapi;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TagRepository implements PanacheRepository<Tag> {

    public List<Tag> findByLabel(String label) {
        return list("SELECT p FROM Tag p WHERE p.label = ?1",label);
    }
}
