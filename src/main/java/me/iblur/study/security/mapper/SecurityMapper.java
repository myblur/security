package me.iblur.study.security.mapper;

import me.iblur.study.security.model.User;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author 秦欣
 * @since 2017年06月22日 17:58.
 */
@Repository
public interface SecurityMapper {

    @Select(value = {
        "SELECT USERNAME, PASSWORD, ENABLED, LOCKED ACCOUNT_NON_LOCKED, EXPIRED ACCOUNT_NON_EXPIRED FROM T_USER WHERE" +
                " #{username,jdbcType=VARCHAR}"
    })
    @ResultType(User.class)
    User loadUserByUsername(String username);


}
