package org.motrice.tdocbox

/**
 * A drill is a test case.
 */
class TdbDrill implements Comparable {

  /**
   * Name of this drill
   */
  String name

  /**
   * HTTP verb
   */
  TdbHttpVerb verb

  /**
   * Invocation mode
   */
  TdbMode mode

  /**
   * The method to invoke
   */
  TdbMethod method

  /**
   * Optional query string
   */
  String queryString

  /**
   * Optional delay before execution (seconds)
   */
  Float delay

  SortedSet parameters
  static belongsTo = [suite: TdbSuite]
  static hasMany = [parameters: TdbParameter]
  static constraints = {
    name nullable: false, maxSize: 40, unique: true
    verb nullable: false
    mode nullable: false
    method nullable: false
    queryString nullable: true, maxSize: 400
    delay nullable: true
    parameters nullable: true
  }

  /**
   * Get the body parameter, if present, otherwise null.
   */
  static final PARAM_Q = 'from TdbParameter as p where p.drill.id=? and p.name=?'
  String getBody() {
    def bodyParam = TdbParameter.find(PARAM_Q, [this.id, 'BODY'])
    return bodyParam?.valueEncoded
  }

  /**
   * Get all parameters as a map.
   */
  Map getMap() {
    def map = [:]
    parameters.each {param ->
      map[param.name] = param.valueEncoded
    }
    return map
  }

  String getDebug() {
    def sb = new StringBuilder()
    sb.append('[Drill ').append(name).append(':')
    def sep = ' '
    map.each {entry ->
      sb.append(sep).append(entry.key).append(':')
      String val = entry.value

      if (!val) {
	sb.append('<>')
      } else if (val.size() <= 48) {
	sb.append(val)
      } else {
	sb.append(val.substring(0, 40))
	sb.append('..<').append(val.size()).append('>')
      }

      sep = ', '
    }
    
    sb.append(']')
    return sb.toString()
  }

  String toString() {
    name
  }

  //-------------------- Comparable --------------------

  int hashCode() {
    name.hashCode()
  }

  boolean equals(Object obj) {
    def result = false
    if (obj instanceof TdbDrill) {
      def other = (TdbDrill)obj
      result = name.equals(other.name)
    }

    return result
  }

  int compareTo(Object obj) {
    def other = (TdbDrill)obj
    name.compareTo(other.name)
  }

}
