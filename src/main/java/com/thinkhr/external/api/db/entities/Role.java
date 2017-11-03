package com.thinkhr.external.api.db.entities;

import lombok.Data;
import javax.persistence.*;

/**
 * A role object model.
 * 
 * @author sbhawsar
 * 
 */

@Entity
@Data
@Table(name="thr_role")
public class Role {
    
	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="role_name")
    private String roleName;

    @Column(name="description")
    private String description;
}