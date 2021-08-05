package org.crudapi;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TAG")
public class Tag extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 45)
    public String label;

    @ManyToMany(mappedBy = "tags",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();

//    Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
        post.getTags().add(this);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ",label='" + label + "'" +
                "}";
    }
}
