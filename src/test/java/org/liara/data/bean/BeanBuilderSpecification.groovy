package org.liara.data.bean


import spock.lang.Specification

class BeanBuilderSpecification extends Specification{
  def "#BeanBuilder instantiate an empty builder" () {
    expect: "the default constructor to instantiate an empty builder"
    new BeanBuilder().beanClass == null
    new BeanBuilder().attributes.isEmpty()
    new BeanBuilder().getters.isEmpty()
    new BeanBuilder().setters.isEmpty()
    new BeanBuilder().types.isEmpty()
  }

  class UserBean {
    private String _firstName
    private String _lastName
    private String _phone
    private boolean _isAdmin

    String getFirstName () {
      return _firstName
    }

    void setFirstName (final String firstName) {
      _firstName = firstName
    }

    int getPhoneSize () {
      return _phone.length()
    }

    String getPhone () {
      return _phone
    }

    String getPhoneOfArea (final String area) {
      return _phone + area
    }

    void setPhone (final String phone) {
      _phone = phone
    }

    void setUsPhone (final String usPhone) {
      _phone = usPhone
    }

    String setBritishPhone (final String britishPhone) {
      return _phone = britishPhone
    }

    String getLastName () {
      return _lastName
    }

    void setLastName (final String lastName) {
      _lastName = lastName
    }

    String getFullName () {
      return _firstName + " " + _lastName
    }

    boolean isAdmin () {
      return _isAdmin
    }

    void setAdmin (boolean isAdmin) {
      _isAdmin = isAdmin
    }
  }

  def "#capture set the given class as the builder current bean class" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan a bean class"
    builder.capture(UserBean.class)

    then: "we expect that the builder bean class was updated"
    builder.beanClass == UserBean.class
  }

  def "#capture find all declared attributes of a given bean" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan a bean class"
    builder.capture(UserBean.class)

    then: "we expect that the builder bean class was updated"
    builder.attributes == (Set<String>) [
      "firstName",
      "phone",
      "usPhone",
      "lastName",
      "fullName",
      "admin",
      "phoneSize",
      "metaClass" // groovy getter / setter
    ]
  }

  def "#capture find all declared attributes types" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan a bean class"
    builder.capture(UserBean.class)

    then: "we expect that the builder bean class was updated"
    builder.types == [
      "firstName": new BeanPropertyType(UserBean.class.getMethod("getFirstName").getAnnotatedReturnType()),
      "phone": new BeanPropertyType(UserBean.class.getMethod("getPhone").getAnnotatedReturnType()),
      "usPhone": new BeanPropertyType(UserBean.class.getMethod("setUsPhone", String.class).getAnnotatedParameterTypes()[0]),
      "lastName": new BeanPropertyType(UserBean.class.getMethod("getLastName").getAnnotatedReturnType()),
      "fullName": new BeanPropertyType(UserBean.class.getMethod("getFullName").getAnnotatedReturnType()),
      "admin": new BeanPropertyType(UserBean.class.getMethod("isAdmin").getAnnotatedReturnType()),
      "phoneSize": new BeanPropertyType(UserBean.class.getMethod("getPhoneSize").getAnnotatedReturnType()),
      "metaClass": new BeanPropertyType(UserBean.class.getMethod("getMetaClass").getAnnotatedReturnType()) // groovy getter / setter
    ]
  }

  def "#capture find all declared attributes getters" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan a bean class"
    builder.capture(UserBean.class)

    then: "we expect that the builder bean class was updated"
    builder.getters == [
      "firstName": UserBean.class.getMethod("getFirstName"),
      "phone": UserBean.class.getMethod("getPhone"),
      "lastName": UserBean.class.getMethod("getLastName"),
      "fullName": UserBean.class.getMethod("getFullName"),
      "admin": UserBean.class.getMethod("isAdmin"),
      "phoneSize": UserBean.class.getMethod("getPhoneSize"),
      "metaClass": UserBean.class.getMethod("getMetaClass") // groovy getter / setter
    ]
  }

  class InvalidSetterTypeBean {
    private String _firstName

    String getFirstName () {
      return _firstName
    }

    void setFirstName (final StringBuilder builder) {
      _firstName = builder.toString()
    }
  }

  def "#capture throws an error if an attribute type is not consistent between a getter and a setter" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan an invalid bean class"
    builder.capture(InvalidSetterTypeBean.class)

    then: "we expect that the builder to thrown an error"
    thrown(Error.class)
  }

  class InvalidGetterTypeBean {
    private String _firstName

    void setFirstName (final StringBuilder builder) {
      _firstName = builder.toString()
    }

    String getFirstName () {
      return _firstName
    }
  }

  def "#capture throws an error if an attribute type is not consistent between a setter and a getter" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan an invalid bean class"
    builder.capture(InvalidGetterTypeBean.class)

    then: "we expect that the builder to thrown an error"
    thrown(Error.class)
  }

  class InvalidDoubleSetterBean {
    private String _firstName

    String getFirstName () {
      return _firstName
    }

    void setFirstName (final String value) {
      _firstName = value
    }

    void setFirstName (final StringBuilder builder) {
      _firstName = builder.toString()
    }
  }

  def "#capture throws an error if a setter is defined twice" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan an invalid bean class"
    builder.capture(InvalidDoubleSetterBean.class)

    then: "we expect that the builder to thrown an error"
    thrown(Error.class)
  }

  class InvalidDoubleGetterBean {
    private String _firstName

    String getFirstName () {
      return _firstName
    }

    String isFirstName () {
      return _firstName
    }

    void setFirstName (final String value) {
      _firstName = value
    }
  }

  def "#capture throws an error if a getter is defined twice" () {
    given: "an empty bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we scan an invalid bean class"
    builder.capture(InvalidDoubleGetterBean.class)

    then: "we expect that the builder to thrown an error"
    thrown(Error.class)
  }

  def "#clear reset the builder" () {
    given: "a bean builder that captured a given bean structure"
    final BeanBuilder builder = new BeanBuilder()
    builder.capture(UserBean.class)

    when: "we clear the builder"
    builder.clear()

    then: "we expect that the builder reset its state"
    builder.beanClass == null
    builder.setters.isEmpty()
    builder.getters.isEmpty()
    builder.types.isEmpty()
    builder.attributes.isEmpty()
  }
}
