## Package overview

The copybook tool simplifies the process of transforming copybook definitions into Ballerina code.

### Command for Ballerina Copybook type generation

```shell
$bal copybook -i <input> -o <output>
```

| Argument                      | Description                                                                                                                                                                                                                                                                    |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| -i, --input                   | The `input` parameter specifies the path of the copybook definition file (e.g., Copybook.cpy). This parameter is mandatory.                                                                                                                                                    |
| -o, --output                  | The `output` parameter specifies the path of the output location of the generated Ballerina type file. This parameter is optional. If this parameter is not specified, the service files will be generated at the same location from which the `copybook` command is executed. |
