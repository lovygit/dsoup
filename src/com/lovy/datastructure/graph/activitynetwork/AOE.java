package com.lovy.datastructure.graph.activitynetwork;

/**
 *
 * Created by asus on 2017/4/13.
 */
public class AOE<V> {

    /*
    the earliest time:
    at adjacent list,j->k,initial earliest array with 0
    if(earliest[k]<earliest[j]+j.during)
        earliest[k]=earliest[j]+j.during
     */

    /*
    the latest time:
    at reverse adjacent list,k->j initial latest array with the max value in earlies array
    if(latest[k]>latest[j]+j.during)
        latest[k]=latest[j]+j.during
     */
}
