package com.ps.repos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ps.base.PetType;
import com.ps.ents.Pet;
import com.ps.ents.User;
import com.ps.repos.PetRepo;

@Repository
public class JdbcTemplatePetRepo implements PetRepo {
	private RowMapper<Pet> rowMapper = new PetRowMapper();

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplatePetRepo(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Set<Pet> findByOwner(User owner) {
		String sql = "select id, name, age, pet_type from p_pet where owner=" + owner.getId();
		return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
	}

	private class PetRowMapper implements RowMapper<Pet> {
		@Override
		public Pet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long id = rs.getLong("ID");
			String name = rs.getString("NAME");
			Integer age = rs.getInt("AGE");
			PetType type = PetType.valueOf(rs.getString("PET_TYPE"));
			Pet pet = new Pet();
			pet.setId(id);
			pet.setName(name);
			pet.setAge(age);
			pet.setPetType(type);
			return pet;
		}
	}
}
