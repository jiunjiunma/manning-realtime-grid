/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package manning.devices.raw.m2;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class RawRecord extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5667938444120555811L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"RawRecord\",\"namespace\":\"manning.devices.raw.m2\",\"fields\":[{\"name\":\"uuid\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"arrival_time_ms\",\"type\":[\"long\",\"null\"]},{\"name\":\"body\",\"type\":[\"bytes\",\"null\"]},{\"name\":\"body_reference\",\"type\":[{\"type\":\"string\",\"avro.java.string\":\"String\"},\"null\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<RawRecord> ENCODER =
      new BinaryMessageEncoder<RawRecord>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<RawRecord> DECODER =
      new BinaryMessageDecoder<RawRecord>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<RawRecord> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<RawRecord> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<RawRecord> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<RawRecord>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this RawRecord to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a RawRecord from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a RawRecord instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static RawRecord fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String uuid;
  @Deprecated public java.lang.Long arrival_time_ms;
  @Deprecated public java.nio.ByteBuffer body;
  @Deprecated public java.lang.String body_reference;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public RawRecord() {}

  /**
   * All-args constructor.
   * @param uuid The new value for uuid
   * @param arrival_time_ms The new value for arrival_time_ms
   * @param body The new value for body
   * @param body_reference The new value for body_reference
   */
  public RawRecord(java.lang.String uuid, java.lang.Long arrival_time_ms, java.nio.ByteBuffer body, java.lang.String body_reference) {
    this.uuid = uuid;
    this.arrival_time_ms = arrival_time_ms;
    this.body = body;
    this.body_reference = body_reference;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return uuid;
    case 1: return arrival_time_ms;
    case 2: return body;
    case 3: return body_reference;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: uuid = (java.lang.String)value$; break;
    case 1: arrival_time_ms = (java.lang.Long)value$; break;
    case 2: body = (java.nio.ByteBuffer)value$; break;
    case 3: body_reference = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'uuid' field.
   * @return The value of the 'uuid' field.
   */
  public java.lang.String getUuid() {
    return uuid;
  }


  /**
   * Sets the value of the 'uuid' field.
   * @param value the value to set.
   */
  public void setUuid(java.lang.String value) {
    this.uuid = value;
  }

  /**
   * Gets the value of the 'arrival_time_ms' field.
   * @return The value of the 'arrival_time_ms' field.
   */
  public java.lang.Long getArrivalTimeMs() {
    return arrival_time_ms;
  }


  /**
   * Sets the value of the 'arrival_time_ms' field.
   * @param value the value to set.
   */
  public void setArrivalTimeMs(java.lang.Long value) {
    this.arrival_time_ms = value;
  }

  /**
   * Gets the value of the 'body' field.
   * @return The value of the 'body' field.
   */
  public java.nio.ByteBuffer getBody() {
    return body;
  }


  /**
   * Sets the value of the 'body' field.
   * @param value the value to set.
   */
  public void setBody(java.nio.ByteBuffer value) {
    this.body = value;
  }

  /**
   * Gets the value of the 'body_reference' field.
   * @return The value of the 'body_reference' field.
   */
  public java.lang.String getBodyReference() {
    return body_reference;
  }


  /**
   * Sets the value of the 'body_reference' field.
   * @param value the value to set.
   */
  public void setBodyReference(java.lang.String value) {
    this.body_reference = value;
  }

  /**
   * Creates a new RawRecord RecordBuilder.
   * @return A new RawRecord RecordBuilder
   */
  public static manning.devices.raw.m2.RawRecord.Builder newBuilder() {
    return new manning.devices.raw.m2.RawRecord.Builder();
  }

  /**
   * Creates a new RawRecord RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new RawRecord RecordBuilder
   */
  public static manning.devices.raw.m2.RawRecord.Builder newBuilder(manning.devices.raw.m2.RawRecord.Builder other) {
    if (other == null) {
      return new manning.devices.raw.m2.RawRecord.Builder();
    } else {
      return new manning.devices.raw.m2.RawRecord.Builder(other);
    }
  }

  /**
   * Creates a new RawRecord RecordBuilder by copying an existing RawRecord instance.
   * @param other The existing instance to copy.
   * @return A new RawRecord RecordBuilder
   */
  public static manning.devices.raw.m2.RawRecord.Builder newBuilder(manning.devices.raw.m2.RawRecord other) {
    if (other == null) {
      return new manning.devices.raw.m2.RawRecord.Builder();
    } else {
      return new manning.devices.raw.m2.RawRecord.Builder(other);
    }
  }

  /**
   * RecordBuilder for RawRecord instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<RawRecord>
    implements org.apache.avro.data.RecordBuilder<RawRecord> {

    private java.lang.String uuid;
    private java.lang.Long arrival_time_ms;
    private java.nio.ByteBuffer body;
    private java.lang.String body_reference;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(manning.devices.raw.m2.RawRecord.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.uuid)) {
        this.uuid = data().deepCopy(fields()[0].schema(), other.uuid);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.arrival_time_ms)) {
        this.arrival_time_ms = data().deepCopy(fields()[1].schema(), other.arrival_time_ms);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.body)) {
        this.body = data().deepCopy(fields()[2].schema(), other.body);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.body_reference)) {
        this.body_reference = data().deepCopy(fields()[3].schema(), other.body_reference);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing RawRecord instance
     * @param other The existing instance to copy.
     */
    private Builder(manning.devices.raw.m2.RawRecord other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.uuid)) {
        this.uuid = data().deepCopy(fields()[0].schema(), other.uuid);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.arrival_time_ms)) {
        this.arrival_time_ms = data().deepCopy(fields()[1].schema(), other.arrival_time_ms);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.body)) {
        this.body = data().deepCopy(fields()[2].schema(), other.body);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.body_reference)) {
        this.body_reference = data().deepCopy(fields()[3].schema(), other.body_reference);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'uuid' field.
      * @return The value.
      */
    public java.lang.String getUuid() {
      return uuid;
    }


    /**
      * Sets the value of the 'uuid' field.
      * @param value The value of 'uuid'.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder setUuid(java.lang.String value) {
      validate(fields()[0], value);
      this.uuid = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'uuid' field has been set.
      * @return True if the 'uuid' field has been set, false otherwise.
      */
    public boolean hasUuid() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'uuid' field.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder clearUuid() {
      uuid = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'arrival_time_ms' field.
      * @return The value.
      */
    public java.lang.Long getArrivalTimeMs() {
      return arrival_time_ms;
    }


    /**
      * Sets the value of the 'arrival_time_ms' field.
      * @param value The value of 'arrival_time_ms'.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder setArrivalTimeMs(java.lang.Long value) {
      validate(fields()[1], value);
      this.arrival_time_ms = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'arrival_time_ms' field has been set.
      * @return True if the 'arrival_time_ms' field has been set, false otherwise.
      */
    public boolean hasArrivalTimeMs() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'arrival_time_ms' field.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder clearArrivalTimeMs() {
      arrival_time_ms = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'body' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getBody() {
      return body;
    }


    /**
      * Sets the value of the 'body' field.
      * @param value The value of 'body'.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder setBody(java.nio.ByteBuffer value) {
      validate(fields()[2], value);
      this.body = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'body' field has been set.
      * @return True if the 'body' field has been set, false otherwise.
      */
    public boolean hasBody() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'body' field.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder clearBody() {
      body = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'body_reference' field.
      * @return The value.
      */
    public java.lang.String getBodyReference() {
      return body_reference;
    }


    /**
      * Sets the value of the 'body_reference' field.
      * @param value The value of 'body_reference'.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder setBodyReference(java.lang.String value) {
      validate(fields()[3], value);
      this.body_reference = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'body_reference' field has been set.
      * @return True if the 'body_reference' field has been set, false otherwise.
      */
    public boolean hasBodyReference() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'body_reference' field.
      * @return This builder.
      */
    public manning.devices.raw.m2.RawRecord.Builder clearBodyReference() {
      body_reference = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RawRecord build() {
      try {
        RawRecord record = new RawRecord();
        record.uuid = fieldSetFlags()[0] ? this.uuid : (java.lang.String) defaultValue(fields()[0]);
        record.arrival_time_ms = fieldSetFlags()[1] ? this.arrival_time_ms : (java.lang.Long) defaultValue(fields()[1]);
        record.body = fieldSetFlags()[2] ? this.body : (java.nio.ByteBuffer) defaultValue(fields()[2]);
        record.body_reference = fieldSetFlags()[3] ? this.body_reference : (java.lang.String) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<RawRecord>
    WRITER$ = (org.apache.avro.io.DatumWriter<RawRecord>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<RawRecord>
    READER$ = (org.apache.avro.io.DatumReader<RawRecord>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.uuid);

    if (this.arrival_time_ms == null) {
      out.writeIndex(1);
      out.writeNull();
    } else {
      out.writeIndex(0);
      out.writeLong(this.arrival_time_ms);
    }

    if (this.body == null) {
      out.writeIndex(1);
      out.writeNull();
    } else {
      out.writeIndex(0);
      out.writeBytes(this.body);
    }

    if (this.body_reference == null) {
      out.writeIndex(1);
      out.writeNull();
    } else {
      out.writeIndex(0);
      out.writeString(this.body_reference);
    }

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.uuid = in.readString();

      if (in.readIndex() != 0) {
        in.readNull();
        this.arrival_time_ms = null;
      } else {
        this.arrival_time_ms = in.readLong();
      }

      if (in.readIndex() != 0) {
        in.readNull();
        this.body = null;
      } else {
        this.body = in.readBytes(this.body);
      }

      if (in.readIndex() != 0) {
        in.readNull();
        this.body_reference = null;
      } else {
        this.body_reference = in.readString();
      }

    } else {
      for (int i = 0; i < 4; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.uuid = in.readString();
          break;

        case 1:
          if (in.readIndex() != 0) {
            in.readNull();
            this.arrival_time_ms = null;
          } else {
            this.arrival_time_ms = in.readLong();
          }
          break;

        case 2:
          if (in.readIndex() != 0) {
            in.readNull();
            this.body = null;
          } else {
            this.body = in.readBytes(this.body);
          }
          break;

        case 3:
          if (in.readIndex() != 0) {
            in.readNull();
            this.body_reference = null;
          } else {
            this.body_reference = in.readString();
          }
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










