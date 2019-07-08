package org.liara.data.bean

import org.liara.data.bean.BeanBuilderSpecification.UserBean
import spock.lang.Specification

class BeanSpecification extends Specification{
  def "#Bean instantiate a bean by using a builder state" () {
    given: "an initialized bean builder"
    final BeanBuilder builder = new BeanBuilder()
    builder.capture(UserBean.class)

    when: "we instantiate a bean from the given builder"
    final Bean bean = new Bean(builder)

    then: "we expect to have a valid bean object"
    builder.beanClass == bean.beanClass
    builder.attributes == new HashSet<>(bean.attributes.toCollection())
    builder.attributes.size() == bean.attributes.size

    for (int index = 0; index < bean.attributes.size; ++index) {
      final String attribute = bean.attributes.get(index)
      builder.getters.getOrDefault(attribute, null) == bean.getters.get(index)
      builder.setters.getOrDefault(attribute, null) == bean.setters.get(index)
      builder.types.get(attribute) == bean.types.get(index)
    }
  }

  def "#Bean throws an error if the builder was not initialized" () {
    given: "an uninitialized bean builder"
    final BeanBuilder builder = new BeanBuilder()

    when: "we instantiate a bean from the given builder"
    new Bean(builder)

    then: "we expect the constructor to throws an error"
    thrown(NullPointerException.class)
  }

  def "#hasAttribute return true if the bean contains the given attribute" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    expect: "#hasAttribute to return true if the bean contains the given attribute"
    bean.hasAttribute("firstName")
    bean.hasAttribute("fullName")
    bean.hasAttribute("usPhone")
    !bean.hasAttribute("blug")
  }

  def "#getAttributeIndex return the index of the given attribute" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    expect: "#getAttributeIndex to return the index of the given attribute"
    for (int index = 0; index < bean.attributes.size; ++index) {
      bean.getAttributeIndex(bean.attributes.get(index)) == index
    }
  }

  def "#getTypeOf return the type of the given attribute" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    expect: "#getTypeOf to return the type of the given attribute"
    for (int index = 0; index < bean.attributes.size; ++index) {
      bean.getTypeOf(bean.attributes.get(index)) == bean.types.get(index)
    }
  }

  def "#getGetterOf return the getter of the given attribute if exists" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    expect: "#getGetterOf to return the getter of the given attribute if exists"
    for (int index = 0; index < bean.attributes.size; ++index) {
      bean.getGetterOf(bean.attributes.get(index)) == bean.getters.get(index)
    }
  }

  def "#getSetterOf return the setter of the given attribute if exists" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    expect: "#getSetterOf to return the setter of the given attribute if exists"
    for (int index = 0; index < bean.attributes.size; ++index) {
      bean.getSetterOf(bean.attributes.get(index)) == bean.setters.get(index)
    }
  }

  def "#isReadable return true if a getter exists for the given attribute" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    expect: "#isReadable to return true if a getter exists for the given attribute"
    for (int index = 0; index < bean.attributes.size; ++index) {
      bean.isReadable(bean.attributes.get(index)) == (bean.getters.get(index) != null)
    }
  }

  def "#isWritable return true if a setter exists for the given attribute" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    expect: "#isWritable to return true if a setter exists for the given attribute"
    for (int index = 0; index < bean.attributes.size; ++index) {
      bean.isWritable(bean.attributes.get(index)) == (bean.setters.get(index) != null)
    }
  }

  def "#get return the value of an attribute for a given instance" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    and: "a bean instance"
    final UserBean user = new UserBean()
    user.admin = true
    user.firstName = "Piwii"
    user.lastName = "McMuffin"

    expect: "#get to return the value of an attribute for a given instance"
    bean.get(user, "firstName") == user.firstName
    bean.get(user, "lastName") == user.lastName
    bean.get(user, "fullName") == user.fullName
    bean.get(user, "admin") == user.admin
  }

  def "#get throws an error if the attribute is not readable" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    and: "a bean instance"
    final UserBean user = new UserBean()

    when: "we call #get on an attribute that is not readable"
    bean.get(user, "usPhone")

    then: "we expect the bean instance to throw"
    thrown(Error.class)
  }

  def "#set update the value of an attribute for a given instance" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    and: "a bean instance"
    final UserBean user = new UserBean()

    when: "we call #set to update given instance"
    bean.set(user, "firstName", "Piwii")
    bean.set(user, "lastName", "McMuffin")
    bean.set(user, "admin", true)

    then: "we expect the instance to have been updated"
    user.admin
    user.firstName == "Piwii"
    user.lastName == "McMuffin"
  }

  def "#set throws an error if the attribute to update is not writable" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    and: "a bean instance"
    final UserBean user = new UserBean()

    when: "we call #set on an attribute that is not writable"
    bean.set(user, "fullName", "Piwii McMuffin")

    then: "we expect the bean instance to throw"
    thrown(Error.class)
  }

  def "#set throws an error if the update value if of a wrong type" () {
    given: "a bean"
    final Bean bean = Bean.instantiate(UserBean.class)

    and: "a bean instance"
    final UserBean user = new UserBean()

    when: "we call #set on an attribute to update with a wrong value type"
    bean.set(user, "fullName", [2, 6, 9])

    then: "we expect the bean instance to throw"
    thrown(Error.class)
  }
}
