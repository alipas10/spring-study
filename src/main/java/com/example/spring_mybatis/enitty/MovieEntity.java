package com.example.spring_mybatis.enitty;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.spring_mybatis.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
public final class MovieEntity extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank
    private String title;
    
    @Column(nullable = false)
    @NotNull
    private int releaseYear;
    
    
    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public int getReleaseYear() {
		return releaseYear;
	}



	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}



	public MovieEntity(String title, int releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }



	public MovieEntity(Long id, @NotBlank String title, @NotNull int releaseYear) {
		super();
		this.id = id;
		this.title = title;
		this.releaseYear = releaseYear;
	}



	public MovieEntity() {
		super();
	}
	
	
}
