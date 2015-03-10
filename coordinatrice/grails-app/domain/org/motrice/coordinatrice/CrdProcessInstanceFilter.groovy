package org.motrice.coordinatrice

/**
 * Filter for querying historic process instances.
 * The filter is persisted and retrieved by its id.
 * The components are joined by an implicit AND condition.
 * This domain has no controller.
 * It is auxiliary to HistoricProcessInstance.
 */
class CrdProcessInstanceFilter {

  final static String ORDER_BY_START_TIME = 'startTime'
  final static String ORDER_BY_END_TIME = 'endTime'
  final static String ORDER_BY_PROC_DEF = 'procDef'

  final static String ORDER_ASCENDING = 'orderAsc'
  final static String ORDER_DESCENDING = 'orderDesc'

  /**
   * Session id.
   */
  String session

  /**
   * Creation time to allow for periodic purges.
   * Automatic timestamping.
   */
  Date dateCreated

  /**
   * Process instance state.
   * 0: any state, 1: finished only, 4: unfinished only
   */
  Integer finishedState

  /**
   * Started before this time.
   */
  Date startedBefore

  /**
   * Use the started before condition.
   */
  Boolean startedBeforeFlag

  /**
   * Started after this time.
   */
  Date startedAfter

  /**
   * Use the started after condition.
   */
  Boolean startedAfterFlag

  /**
   * Finished before this time.
   */
  Date finishedBefore

  /**
   * Use the finished before condition.
   */
  Boolean finishedBeforeFlag

  /**
   * Finished after this time.
   */
  Date finishedAfter

  /**
   * Use the finished after condition.
   */
  Boolean finishedAfterFlag

  /**
   * Started by this user id.
   */
  String startedBy

  /**
   * Only this process definition id.
   */
  String procdefId

  /**
   * Exclude these process definition keys.
   * The format is a comma-separated list of process definition keys (String).
   * A process definition key may not contain "funny" characters.
   */
  String procdefExcludeKey

  /**
   * Name of process instance variable to examine.
   */
  String variableName
  
  /**
   * Variable value pattern.
   */
  String variablePattern

  /**
   * Property to use for ordering the result.
   * Values: 'orderStartTime', 'orderEndTime', 'orderProcDef'.
   */
  String orderByProperty

  /**
   * Ordering direction.
   * Values: 'orderAsc', 'orderDesc'.
   */
  String orderDirection

  /**
   * Exclude subprocesses?
   */
  Boolean excludeSubprocesses

  static constraints = {
    session nullable: true, maxSize: 200
    dateCreated nullable: true
    finishedState nullable: true, range: 0..4
    startedBefore nullable: true
    startedBeforeFlag nullable: true
    startedAfter nullable: true
    startedAfterFlag nullable: true
    finishedBefore nullable: true
    finishedBeforeFlag nullable: true
    finishedAfter nullable: true
    finishedAfterFlag nullable: true
    startedBy nullable: true, maxSize: 40
    procdefId nullable: true, maxSize: 80
    procdefExcludeKey nullable: true, maxSize: 400
    variableName nullable: true, maxSize: 80
    variablePattern nullable: true, maxSize: 120
    orderByProperty nullable: true, maxSize: 24
    orderDirection nullable: true, maxSize: 24
    excludeSubprocesses nullable: true
  }

  /**
   * Get the process definition keys to exclude as a List of String.
   */
  List getExcludeKeys() {
    procdefExcludeKey? procdefExcludeKey.split(',').toList() : []
  }

  String toString() {
    def sb = new StringBuilder()
    sb.append('[ProcInstFilter(').append(id?:'').append(')')
    if (finishedState != null) sb.append(' finishedState:').append(finishedState)
    if (startedBeforeFlag) sb.append(' startedBefore:').append(startedBefore)
    if (startedAfterFlag) sb.append(' startedAfter:').append(startedAfter)
    if (startedBy) sb.append(' startedBy:').append(startedBy)
    if (finishedBeforeFlag) sb.append(' finishedBefore:').append(finishedBefore)
    if (finishedAfterFlag) sb.append(' finishedAfter:').append(finishedAfter)
    if (procdefId) sb.append(' procdefId:').append(procdefId)
    if (procdefExcludeKey) sb.append(' procdefExcludeKey[').append(procdefExcludeKey).append(']')
    if (variableName) sb.append(' variableName|').append(variableName).append('|')
    if (variablePattern) sb.append(' variablePattern|').append(variablePattern).append('|')
    if (orderByProperty) sb.append(' orderByProperty:').append(orderByProperty)
    if (orderDirection) sb.append(' orderDirection:').append(orderDirection)
    if (excludeSubprocesses) sb.append(' excludeSubprocesses:').append(excludeSubprocesses)
    sb.append(']')
    return sb.toString()
  }

}
