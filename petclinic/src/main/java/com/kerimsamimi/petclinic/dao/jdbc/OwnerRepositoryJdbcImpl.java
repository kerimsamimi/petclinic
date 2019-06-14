package com.kerimsamimi.petclinic.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kerimsamimi.petclinic.dao.OwnerRepository;
import com.kerimsamimi.petclinic.model.Owner;

@Repository
public class OwnerRepositoryJdbcImpl implements OwnerRepository {
	
	@Autowired
	private JdbcTemplate jdcbTemplate;
	
	
	private RowMapper<Owner> rowMapper=new RowMapper<Owner>() {

		@Override
		public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
			Owner owner= new Owner();
			owner.setId(rs.getLong("id"));
			owner.setFirstName("first_name");
			owner.setLastName("last_name");
			return owner;
		}
	};
	
	
	@Override
	public List<Owner> findAll() {
		String sql="select id,first_name,last_name from t_owner";
		return jdcbTemplate.query(sql, rowMapper);
	}

	@Override
	public Owner findById(Long id) {
		String sql= "select id,first_name,last_name from t_owner where id = ?";
		return DataAccessUtils.singleResult(jdcbTemplate.query(sql, rowMapper, id));
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		String sql= "select id,first_name,last_name from t_owner where last_name like ?";
		return jdcbTemplate.query(sql, rowMapper, "%" + lastName + "%");
	}

	@Override
	public void create(Owner owner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Owner update(Owner owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		String sql="delete from t_owner where id = ?";
		jdcbTemplate.update(sql,id);

	}

}
