# gRPC

## Overview
- Only the Order Service is implemented the gRPC APIs.

## Generate Stub
- The location of the order service's proto file: `ftgo-order-service/src/main/proto/OrderService.proto`
   - Options
     | Option | Description |
     |----|----|
     | `java_multiple_files` | <ul><li>If `true`, it would generate multiple stub files for different message types respectively.<li>If `false`, it would generate only one stub file including all the message types.</ul> |
     | `java_package` | <ul><li>The packake path of where you want to put your stub file (You need to manually move the stub file to this package path after generating it)</ul> |
     | `java_outer_classname` | <ul><li>The name of the stub file.</ul> |
     | `objc_class_prefix` | <ul><li>Specify a prefix to be used for your generated classes.</ul>
     
- Command for generating stub
  `./gradlew :ftgo-order-service:generateProto`

- Copy the stub file into the java package specified in `java_package` option.
   - The stub file will be generated in: `ftgo-order-service/build/generated/source/proto/main/java`.
