package me.iblur.study.security.mapper;

import me.iblur.study.security.authentication.UrlAuthority;
import me.iblur.study.security.model.AuthUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 秦欣
 * @since 2017年06月22日 17:58.
 */
@Mapper
public interface SecurityMapper {

    @Select(value = {
            "SELECT u.ID, u.USERNAME, u.PASSWORD, u.ENABLED, u.LOCKED, u.EXPIRED, r.NAME ROLE_NAME " +
                    "FROM T_USER u, T_USER_ROLE ur, T_ROLE r WHERE u.ID = ur.USER_ID AND r.ID = ur.ROLE_ID AND u" +
                    ".USERNAME = #{username,jdbcType=VARCHAR}"
    })
    @ResultMap(value = {"AuthUserResultMap"})
    AuthUser loadUserByUsername(@Param("username") String username);


    @Select(value = {
            "SELECT a.URL, a.IP_ADDRESS, a.METHOD, r. NAME ROLE_NAME FROM T_ROLE r, T_ROLE_AUTHORITY ra, T_AUTHORITY " +
                    "a WHERE" +
                    " r.ID = ra.ROLE_ID AND a.ID = ra.AUTHORITY_ID"
    })
    @ResultType(UrlAuthority.class)
    List<UrlAuthority> loadAllAuthority();
}
