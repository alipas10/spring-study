package com.example.spring_mybatis.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.example.spring_mybatis.enitty.EmployeeEntity;


@Mapper
public interface EmployeeMapper {
	
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert (" insert into employee ( name, email ) values ( #{name},#{email} )")
//	@SelectKey ( statement = "CALL IDENTITY()", before = false,keyColumn = "id"
//		,keyProperty = "id", resultType = Integer.class)
	public Integer insert(EmployeeEntity emp);
	
	@Select (" select * from employee  where id = #{id} ")
	public Optional<EmployeeEntity> findByID(Integer id);
	
	@Select (" select * from employee  where name = #{name} ")
//	@Results({
//	     @Result(property = "id", column = "id"),
//	     @Result(property = "name", column = "name"),
//	     @Result(property = "email" ,column = "email" ), 
//	   })
	public List<EmployeeEntity> findByName(String name);

	@Update (" update employee set name = #{name}, email = #{email} ")
//	@SelectKey ( statement = "select * from employee where id = #{id} ", before = false,keyColumn = "id,name,email",
//		keyProperty = "id,name,email", resultType = EmployeeDTO.class)
	public void updateEmployee(EmployeeEntity empDTO);
	
	@Delete (" delete from employee where id = #{id }")
	public void deleteEployee(EmployeeEntity empDTO);
}
