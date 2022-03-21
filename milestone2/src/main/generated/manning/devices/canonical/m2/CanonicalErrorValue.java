/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package manning.devices.canonical.m2;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class CanonicalErrorValue extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5265540044663618931L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CanonicalErrorValue\",\"namespace\":\"manning.devices.canonical.m2\",\"fields\":[{\"name\":\"stack_trace\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}},{\"name\":\"raw_record_bytes\",\"type\":\"bytes\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CanonicalErrorValue> ENCODER =
      new BinaryMessageEncoder<CanonicalErrorValue>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CanonicalErrorValue> DECODER =
      new BinaryMessageDecoder<CanonicalErrorValue>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CanonicalErrorValue> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CanonicalErrorValue> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CanonicalErrorValue> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CanonicalErrorValue>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CanonicalErrorValue to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CanonicalErrorValue from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CanonicalErrorValue instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CanonicalErrorValue fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.util.List<java.lang.String> stack_trace;
  @Deprecated public java.nio.ByteBuffer raw_record_bytes;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CanonicalErrorValue() {}

  /**
   * All-args constructor.
   * @param stack_trace The new value for stack_trace
   * @param raw_record_bytes The new value for raw_record_bytes
   */
  public CanonicalErrorValue(java.util.List<java.lang.String> stack_trace, java.nio.ByteBuffer raw_record_bytes) {
    this.stack_trace = stack_trace;
    this.raw_record_bytes = raw_record_bytes;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return stack_trace;
    case 1: return raw_record_bytes;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: stack_trace = (java.util.List<java.lang.String>)value$; break;
    case 1: raw_record_bytes = (java.nio.ByteBuffer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'stack_trace' field.
   * @return The value of the 'stack_trace' field.
   */
  public java.util.List<java.lang.String> getStackTrace() {
    return stack_trace;
  }


  /**
   * Sets the value of the 'stack_trace' field.
   * @param value the value to set.
   */
  public void setStackTrace(java.util.List<java.lang.String> value) {
    this.stack_trace = value;
  }

  /**
   * Gets the value of the 'raw_record_bytes' field.
   * @return The value of the 'raw_record_bytes' field.
   */
  public java.nio.ByteBuffer getRawRecordBytes() {
    return raw_record_bytes;
  }


  /**
   * Sets the value of the 'raw_record_bytes' field.
   * @param value the value to set.
   */
  public void setRawRecordBytes(java.nio.ByteBuffer value) {
    this.raw_record_bytes = value;
  }

  /**
   * Creates a new CanonicalErrorValue RecordBuilder.
   * @return A new CanonicalErrorValue RecordBuilder
   */
  public static manning.devices.canonical.m2.CanonicalErrorValue.Builder newBuilder() {
    return new manning.devices.canonical.m2.CanonicalErrorValue.Builder();
  }

  /**
   * Creates a new CanonicalErrorValue RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CanonicalErrorValue RecordBuilder
   */
  public static manning.devices.canonical.m2.CanonicalErrorValue.Builder newBuilder(manning.devices.canonical.m2.CanonicalErrorValue.Builder other) {
    if (other == null) {
      return new manning.devices.canonical.m2.CanonicalErrorValue.Builder();
    } else {
      return new manning.devices.canonical.m2.CanonicalErrorValue.Builder(other);
    }
  }

  /**
   * Creates a new CanonicalErrorValue RecordBuilder by copying an existing CanonicalErrorValue instance.
   * @param other The existing instance to copy.
   * @return A new CanonicalErrorValue RecordBuilder
   */
  public static manning.devices.canonical.m2.CanonicalErrorValue.Builder newBuilder(manning.devices.canonical.m2.CanonicalErrorValue other) {
    if (other == null) {
      return new manning.devices.canonical.m2.CanonicalErrorValue.Builder();
    } else {
      return new manning.devices.canonical.m2.CanonicalErrorValue.Builder(other);
    }
  }

  /**
   * RecordBuilder for CanonicalErrorValue instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CanonicalErrorValue>
    implements org.apache.avro.data.RecordBuilder<CanonicalErrorValue> {

    private java.util.List<java.lang.String> stack_trace;
    private java.nio.ByteBuffer raw_record_bytes;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(manning.devices.canonical.m2.CanonicalErrorValue.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.stack_trace)) {
        this.stack_trace = data().deepCopy(fields()[0].schema(), other.stack_trace);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.raw_record_bytes)) {
        this.raw_record_bytes = data().deepCopy(fields()[1].schema(), other.raw_record_bytes);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing CanonicalErrorValue instance
     * @param other The existing instance to copy.
     */
    private Builder(manning.devices.canonical.m2.CanonicalErrorValue other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.stack_trace)) {
        this.stack_trace = data().deepCopy(fields()[0].schema(), other.stack_trace);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.raw_record_bytes)) {
        this.raw_record_bytes = data().deepCopy(fields()[1].schema(), other.raw_record_bytes);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'stack_trace' field.
      * @return The value.
      */
    public java.util.List<java.lang.String> getStackTrace() {
      return stack_trace;
    }


    /**
      * Sets the value of the 'stack_trace' field.
      * @param value The value of 'stack_trace'.
      * @return This builder.
      */
    public manning.devices.canonical.m2.CanonicalErrorValue.Builder setStackTrace(java.util.List<java.lang.String> value) {
      validate(fields()[0], value);
      this.stack_trace = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'stack_trace' field has been set.
      * @return True if the 'stack_trace' field has been set, false otherwise.
      */
    public boolean hasStackTrace() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'stack_trace' field.
      * @return This builder.
      */
    public manning.devices.canonical.m2.CanonicalErrorValue.Builder clearStackTrace() {
      stack_trace = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'raw_record_bytes' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getRawRecordBytes() {
      return raw_record_bytes;
    }


    /**
      * Sets the value of the 'raw_record_bytes' field.
      * @param value The value of 'raw_record_bytes'.
      * @return This builder.
      */
    public manning.devices.canonical.m2.CanonicalErrorValue.Builder setRawRecordBytes(java.nio.ByteBuffer value) {
      validate(fields()[1], value);
      this.raw_record_bytes = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'raw_record_bytes' field has been set.
      * @return True if the 'raw_record_bytes' field has been set, false otherwise.
      */
    public boolean hasRawRecordBytes() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'raw_record_bytes' field.
      * @return This builder.
      */
    public manning.devices.canonical.m2.CanonicalErrorValue.Builder clearRawRecordBytes() {
      raw_record_bytes = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CanonicalErrorValue build() {
      try {
        CanonicalErrorValue record = new CanonicalErrorValue();
        record.stack_trace = fieldSetFlags()[0] ? this.stack_trace : (java.util.List<java.lang.String>) defaultValue(fields()[0]);
        record.raw_record_bytes = fieldSetFlags()[1] ? this.raw_record_bytes : (java.nio.ByteBuffer) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CanonicalErrorValue>
    WRITER$ = (org.apache.avro.io.DatumWriter<CanonicalErrorValue>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CanonicalErrorValue>
    READER$ = (org.apache.avro.io.DatumReader<CanonicalErrorValue>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    long size0 = this.stack_trace.size();
    out.writeArrayStart();
    out.setItemCount(size0);
    long actualSize0 = 0;
    for (java.lang.String e0: this.stack_trace) {
      actualSize0++;
      out.startItem();
      out.writeString(e0);
    }
    out.writeArrayEnd();
    if (actualSize0 != size0)
      throw new java.util.ConcurrentModificationException("Array-size written was " + size0 + ", but element count was " + actualSize0 + ".");

    out.writeBytes(this.raw_record_bytes);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      long size0 = in.readArrayStart();
      java.util.List<java.lang.String> a0 = this.stack_trace;
      if (a0 == null) {
        a0 = new SpecificData.Array<java.lang.String>((int)size0, SCHEMA$.getField("stack_trace").schema());
        this.stack_trace = a0;
      } else a0.clear();
      SpecificData.Array<java.lang.String> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<java.lang.String>)a0 : null);
      for ( ; 0 < size0; size0 = in.arrayNext()) {
        for ( ; size0 != 0; size0--) {
          java.lang.String e0 = (ga0 != null ? ga0.peek() : null);
          e0 = in.readString();
          a0.add(e0);
        }
      }

      this.raw_record_bytes = in.readBytes(this.raw_record_bytes);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          long size0 = in.readArrayStart();
          java.util.List<java.lang.String> a0 = this.stack_trace;
          if (a0 == null) {
            a0 = new SpecificData.Array<java.lang.String>((int)size0, SCHEMA$.getField("stack_trace").schema());
            this.stack_trace = a0;
          } else a0.clear();
          SpecificData.Array<java.lang.String> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<java.lang.String>)a0 : null);
          for ( ; 0 < size0; size0 = in.arrayNext()) {
            for ( ; size0 != 0; size0--) {
              java.lang.String e0 = (ga0 != null ? ga0.peek() : null);
              e0 = in.readString();
              a0.add(e0);
            }
          }
          break;

        case 1:
          this.raw_record_bytes = in.readBytes(this.raw_record_bytes);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}









