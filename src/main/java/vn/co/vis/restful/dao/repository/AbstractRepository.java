package vn.co.vis.restful.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;
import vn.co.vis.restful.log.AppLogger;
import vn.co.vis.restful.log.LoggerFactory;
import vn.co.vis.restful.log.LoggerType;
import vn.co.vis.restful.utility.StringUtils;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Common attribute and method for repository
 * Using to access to DB
 * @author Giang Thanh Quang
 * @since 10/16/2020
 */
public class AbstractRepository {

    @Autowired
    @Qualifier("dataSource")
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected static final AppLogger SQL_LOGGER = LoggerFactory.getLogger(LoggerType.SQL);

    /**
     * Get list attribute of a class
     *
     * @param clazz class to get
     * @return List<String> attribute
     */
    protected List<String> listAttributeName(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
    }

    /**
     * Get table name as snake case from class
     *
     * @param clazz class to get
     * @return table name as snake case
     */
    protected String getSimpleNameTable(Class<?> clazz) {
        return StringUtils.camelCaseToSnakeCase(clazz.getSimpleName()).toLowerCase();
    }

    /**
     * Get attribute names for select query
     *
     * @param clazz class to get
     * @return a string as a select in query
     */
    protected String attributeNamesForSelect(Class<?> clazz) {
        List<String> listAttribute = listAttributeName(clazz);
        if (listAttribute == null || listAttribute.isEmpty()) {
            return "";
        }
        //Covert CamelCase to SnakeCase
        String attributeNames = listAttribute.stream().map(StringUtils::camelCaseToSnakeCase)
                .collect(Collectors.joining(","));
        return attributeNames;
    }

    /**
     * Quick create sql query
     *
     * @param clazz       Class with camel case attributes map to database using snake case fields
     * @param prefix      such as A, B, C
     * @param nameNotUses fields not used
     * @return such as A.column1, A.column_anything
     */
    protected String attributeNamesForSelectWithPrefix(Class<?> clazz, @NotNull String prefix, String... nameNotUses) {
        List<String> listAttribute = listAttributeName(clazz);
        if (listAttribute == null || listAttribute.isEmpty()) {
            return "";
        }
        //Covert CamelCase to SnakeCase
        String attributeNames = listAttribute.stream()
                .filter(s -> {
                    if (nameNotUses == null) {
                        return true;
                    }
                    for (String name : nameNotUses) {
                        if (name.equals(s)) {
                            return false;
                        }
                    }
                    return true;
                })
                .map(s -> prefix + "." + StringUtils.camelCaseToSnakeCase(s))
                .collect(Collectors.joining(","));
        return attributeNames;
    }

    /**
     * Create query in select sql, using class and some attribute not use
     *
     * @param clazz       class to get
     * @param nameNotUses name not use
     * @return a string as query for select
     */
    protected String attributeNamesForSelect(Class<?> clazz, List<String> nameNotUses) {
        if (nameNotUses == null || nameNotUses.isEmpty()) {
            return attributeNamesForSelect(clazz);
        }
        List<String> listAttribute = listAttributeName(clazz);
        if (listAttribute.isEmpty()) return "";
        //Covert CamelCase to SnakeCase
        String attributeNames = listAttribute.stream().filter(attr -> {
            for (String name : nameNotUses) {
                if (name.equals(attr)) {
                    return false;
                }
            }
            return true;
        }).map(StringUtils::camelCaseToSnakeCase)
                .collect(Collectors.joining(";"));
        return attributeNames;
    }

    /**
     * Get attribute names for a select
     *
     * @param clazz class to get
     * @param first prefix of attribute
     * @param last  suffix of attribute
     * @return a string as query for select
     */
    protected String attributeNames(Class<?> clazz, String first, String last) {
        List<String> listAttribute = listAttributeName(clazz);
        if (CollectionUtils.isEmpty(listAttribute)) {
            return "";
        }

        String attributeNames = listAttribute.stream().map(attr -> {
                    String snakeCaseAttr = StringUtils.camelCaseToSnakeCase(attr);
                    if (first != null) {
                        snakeCaseAttr = first + snakeCaseAttr;
                    }

                    if (last != null) {
                        snakeCaseAttr = snakeCaseAttr + last;
                    }

                    return snakeCaseAttr;
                }
        ).collect(Collectors.joining(","));

        return attributeNames;
    }

    /**
     * Get attribute names for a select
     *
     * @param clazz       class to create
     * @param nameNotUses name of attribute that not use
     * @param first       prefix of attribute
     * @param last        suffix of attribute
     * @return a string as query for select
     */
    protected String attributeNames(Class<?> clazz, List<String> nameNotUses, String first, String last) {
        if (nameNotUses == null || nameNotUses.isEmpty()) {
            return attributeNames(clazz, first, last);
        }
        List<String> listAttribute = listAttributeName(clazz);
        if (listAttribute == null || listAttribute.isEmpty()) {
            return "";
        }
        String attributeNames = listAttribute.stream().filter(attr -> {
            for (String name : nameNotUses) {
                if (name.equals(attr)) {
                    return false;
                }
            }
            return true;
        }).map(attr -> {
                    String snakeCaseAttr = StringUtils.camelCaseToSnakeCase(attr);
                    if (first != null) {
                        snakeCaseAttr = first + snakeCaseAttr;
                    }

                    if (last != null) {
                        snakeCaseAttr = snakeCaseAttr + last;
                    }

                    return snakeCaseAttr;
                }
        ).collect(Collectors.joining(","));

        return attributeNames;
    }

}
