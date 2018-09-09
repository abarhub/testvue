// new Vue({
//     el: '#app',
//     data: {
//         message: 'Hello Vue2.js!'
//     }
// })
function test() {
    //test1();
    //test2();
    //test3();
    test4();
}


function test4() {
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue.js!',
            message2: 'Hello Vue2.js!'
        },
        methods: {
            reverseMessage: function () {

                //window.prompt("mot de passe","defaultText");

                $.ajax({
                    url: '/test4',
                    type: 'post',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (data) {

                        console.info("data=", data);

                        var id = data.id;
                        var publickeyPem = data.publickey;

                        var messageAEnvoyer = this.message2;

                        var key = forge.random.getBytesSync(16);
                        var iv = forge.random.getBytesSync(16);

                        //key='1234111111111111';

                        console.info("key=", key,key.length);
                        console.info("iv=", iv);

                        var cipher = forge.cipher.createCipher('AES-CBC', key);
                        cipher.start({iv: iv});
                        cipher.update(forge.util.createBuffer(messageAEnvoyer));
                        cipher.finish();
                        var encrypted = cipher.output;

                        console.info("encrypted=", encrypted);

                        var publicKey = forge.pki.publicKeyFromPem(publickeyPem);

                        console.info("publicKey=", publicKey);

                        console.info("key=", key,key.length);

                        //key='1234111111111111';
                        //key='12341111';
                        //key='1';

                        console.info("key avant=", key);

                        key=forge.util.createBuffer(key);
                        //key=forge.util.encode64(key);

                        console.info("key apres=", key);

                        var encryptedKey = publicKey.encrypt(key, 'RSA-OAEP', {
                            md: forge.md.sha256.create(),
                            mgf1: {
                                md: forge.md.sha1.create()
                            }
                        });

                        console.info("encryptedKey=", encryptedKey);

                        var message = {
                            id: id,
                            key: forge.util.encode64(encryptedKey),
                            iv: forge.util.encode64(iv),
                            message: forge.util.encode64(encrypted.data)
                        };

                        console.info("message=", message);

                        $.ajax({
                            url: '/test4bis',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function (data) {
                                //$('#target').html(data.msg);
                                console.info("reponse", data);


                            },
                            data: JSON.stringify(message)
                        });
                    }//,
                    //data: JSON.stringify(demande)
                });

                //return;

                // var rsa = forge.pki.rsa;
                // var keypair = rsa.generateKeyPair({bits: 2048, e: 0x10001});
                // var ct = keypair.publicKey.encrypt("Arbitrary Message Here");
                // var res = keypair.privateKey.decrypt(ct);
                //
                // console.info("res=", res);
                //
                // var pem = forge.pki.publicKeyToPem(keypair.publicKey);
                //
                // console.info("pem=", pem);
                //
                // // var xhttp;
                // // xhttp=new XMLHttpRequest();
                // // xhttp.onreadystatechange = function() {
                // //     if (this.readyState == 4 && this.status == 200) {
                // //         //cFunction(this);
                // //         console.info("coucou");
                // //     }
                // // };
                // // xhttp.open("POST", url, true);
                // // xhttp.send();
                //
                // var demande = {
                //     cle: pem
                // };
                //
                // console.info("demande=", demande);
                //
                // $.ajax({
                //     url: '/test3',
                //     type: 'post',
                //     dataType: 'json',
                //     contentType: 'application/json',
                //     success: function (data) {
                //         //$('#target').html(data.msg);
                //         console.info("reponse", data);
                //
                //         //console.info("res", data.reponse);
                //
                //         var data2=data.cle;
                //
                //         console.info("data2", data2);
                //
                //         var decoded = forge.util.decode64(data2);
                //
                //         console.info("decoded", decoded);
                //
                //         var res2 = keypair.privateKey.decrypt(decoded,'RSA-OAEP', {
                //             md: forge.md.sha256.create(),
                //             mgf1: {
                //                 md: forge.md.sha1.create()
                //             }
                //         });
                //
                //         console.info("res2", res2);
                //
                //         console.info("iv", data.iv);
                //         console.info("key", res2);
                //         console.info("encrypted", data.reponse);
                //
                //         var key0 = forge.pkcs5.pbkdf2('password', 'salt', 1000, 16);
                //         console.info("key0", key0);
                //
                //         var iv=forge.util.decode64(data.iv);
                //         var key2=res2;
                //         var encrypted=forge.util.decode64(data.reponse);
                //
                //         var decipher = forge.cipher.createDecipher('AES-CBC', key2);
                //         decipher.start({iv: iv});
                //         decipher.update(forge.util.createBuffer(encrypted));
                //         var result = decipher.finish(); // check 'result' for true/false
                //         console.log("result",result);
                //         // outputs decrypted hex
                //         console.log(decipher.output.toHex());
                //         console.log("decrypte",decipher.output);
                //
                //     },
                //     data: JSON.stringify(demande)
                // });

            }
        }
    });
}

test();
