/*
 * This file is part of gradoop.
 *
 * gradoop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * gradoop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with gradoop. If not, see <http://www.gnu.org/licenses/>.
 */

package org.gradoop.model.impl.id;

import com.google.common.collect.Sets;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents a set of GradoopIds.
 *
 * @see GradoopId
 */
public class GradoopIds implements Iterable<GradoopId>, Writable, Serializable {

  /**
   * Holds the identifiers.
   */
  private Set<GradoopId> identifiers;

  /**
   * Creates a new instance.
   */
  public GradoopIds() {
    identifiers = Sets.newHashSet();
  }

  /**
   * constructor from multiple given ids
   * @param ids given ids
   * @return gradoop ids
   */
  public static GradoopIds fromExisting(GradoopId... ids) {
    GradoopIds gradoopIds = new GradoopIds();

    for (GradoopId id : ids) {
      gradoopIds.add(id);
    }

    return gradoopIds;
  }

  /**
   * factory method to create gradoop ids from given numbers
   * @param ids numbers
   * @return gradoop ids
   */
  public static GradoopIds fromLongs(Long... ids) {
    GradoopIds gradoopIds = new GradoopIds();

    for (Long id : ids) {
      gradoopIds.add(GradoopId.fromLong(id));
    }

    return gradoopIds;
  }

  /**
   * Adds a GradoopId to the set.
   *
   * @param identifier gradoop identifier
   */
  public void add(GradoopId identifier) {
    identifiers.add(identifier);
  }

  /**
   * Checks if the given GradoopId is contained in the set.
   *
   * @param identifier gradoop identifier
   * @return true, iff the given identifier is in the set
   */
  public boolean contains(GradoopId identifier) {
    return identifiers.contains(identifier);
  }

  /**
   * adds existing gradoop ids
   * @param gradoopIds ids to add
   */
  public void addAll(Collection<GradoopId> gradoopIds) {
    identifiers.addAll(gradoopIds);
  }

  /**
   * adds existing gradoop ids
   * @param gradoopIds ids to add
   */
  public void addAll(GradoopIds gradoopIds) {
    addAll(gradoopIds.identifiers);
  }

  /**
   * checks, if all gradoop ids are contained
   * @param other gradoop ids
   * @return true, if all contained
   */
  public boolean containsAll(GradoopIds other) {
    return this.identifiers.containsAll(other.identifiers);
  }

  /**
   * checks, if all ids of a collection are contained
   * @param identifiers id collection
   * @return true, if all contained
   */
  public boolean containsAll(Collection<GradoopId> identifiers) {
    return this.identifiers.containsAll(identifiers);
  }



  /**
   * checks if empty
   * @return true, if empty
   */
  public boolean isEmpty() {
    return this.identifiers.isEmpty();
  }

  /**
   * returns the contained identifiers as a collection
   * @return collection of identifiers
   */
  public Collection<GradoopId> toCollection() {
    return identifiers;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeInt(identifiers.size());
    for (GradoopId id : identifiers) {
      id.write(dataOutput);
    }
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    int count = dataInput.readInt();
    identifiers = Sets.newHashSetWithExpectedSize(count);

    for (int i = 0; i < count; i++) {
      GradoopId id = new GradoopId();
      id.readFields(dataInput);
      identifiers.add(id);
    }
  }

  @Override
  public Iterator<GradoopId> iterator() {
    return identifiers.iterator();
  }

  /**
   * drops all contained gradoop ids
   */
  public void clear() {
    identifiers.clear();
  }

  /**
   * returns the number of contained gradoop ids
   * @return size
   */
  public int size() {
    return identifiers.size();
  }
}
