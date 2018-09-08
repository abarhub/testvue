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


function test1() {
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue.js!'
        },
        methods: {
            reverseMessage: function () {

                //this.message = this.message.split('').reverse().join('')

                //var keyToDecrypt='';

                window.crypto.subtle.generateKey(
                        {
                            name: "RSASSA-PKCS1-v1_5",
                            modulusLength: 2048, //can be 1024, 2048, or 4096
                            publicExponent: new Uint8Array([0x01, 0x00, 0x01]),
                            hash: {name: "SHA-256"}, //can be "SHA-1", "SHA-256", "SHA-384", or "SHA-512"
                        },
                        true, //whether the key is extractable (i.e. can be used in exportKey)
                        ["sign", "verify"] //can be any combination of "sign" and "verify"
                )
                        .then(function (key) {
                            //returns a keypair object
                            console.log(key);
                            console.log("publicKey", key.publicKey);
                            console.log("privateKey", key.privateKey);
                            var keyToDecrypt = key.publicKey;


                            window.crypto.subtle.exportKey(
                                    "spki",//"jwk",//"pkcs8", //can be "jwk" (public or private), "spki" (public only), or "pkcs8" (private only)
                                    keyToDecrypt//privateKey //can be a publicKey or privateKey, as long as extractable was true
                            )
                                    .then(function (keydata) {
                                        //returns the exported key data
                                        console.log("keydata", keydata);

                                        var enc = new TextDecoder("utf-8");

                                        console.log("TextDecoder", enc.decode(keydata));
                                    })
                                    .catch(function (err) {
                                        console.error("error exportKey", err);
                                    });

                        })
                        .catch(function (err) {
                            console.error(err);
                        });


            }
        }
    });
}


function test2() {
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue.js!'
        },
        methods: {
            reverseMessage: function () {

                //this.message = this.message.split('').reverse().join('')

                //var keyToDecrypt='';

                window.crypto.subtle.generateKey(
                        {
                            name: "RSASSA-PKCS1-v1_5",
                            modulusLength: 2048, //can be 1024, 2048, or 4096
                            publicExponent: new Uint8Array([0x01, 0x00, 0x01]),
                            hash: {name: "SHA-256"}, //can be "SHA-1", "SHA-256", "SHA-384", or "SHA-512"
                        },
                        true, //whether the key is extractable (i.e. can be used in exportKey)
                        ["sign", "verify"] //can be any combination of "sign" and "verify"
                )
                        .then(function (key) {
                            //returns a keypair object
                            console.log(key);
                            console.log("publicKey", key.publicKey);
                            console.log("privateKey", key.privateKey);
                            var keyToDecrypt = key.publicKey;


                            window.crypto.subtle.exportKey(
                                    "jwk",//"jwk",//"pkcs8", //can be "jwk" (public or private), "spki" (public only), or "pkcs8" (private only)
                                    keyToDecrypt//privateKey //can be a publicKey or privateKey, as long as extractable was true
                            )
                                    .then(function (keydata) {
                                        //returns the exported key data
                                        console.log("keydata", keydata);

                                        // var enc = new TextDecoder("utf-8");
                                        //
                                        // console.log("TextDecoder",enc.decode(keydata));
                                        //
                                        // var string_private_key = JSON.stringify(keydata);
                                        //
                                        // console.log("stringify",string_private_key);
                                    })
                                    .catch(function (err) {
                                        console.error("error exportKey", err);
                                    });

                        })
                        .catch(function (err) {
                            console.error(err);
                        });


            }
        }
    });
}


function test3() {
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue.js!'
        },
        methods: {
            reverseMessage: function () {
                var rsa = forge.pki.rsa;
                var keypair = rsa.generateKeyPair({bits: 2048, e: 0x10001});
                var ct = keypair.publicKey.encrypt("Arbitrary Message Here");
                var res = keypair.privateKey.decrypt(ct);

                console.info("res=", res);

                var pem = forge.pki.publicKeyToPem(keypair.publicKey);

                console.info("pem=", pem);
            }
        }
    });
}

function test4() {
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue.js!'
        },
        methods: {
            reverseMessage: function () {
                var rsa = forge.pki.rsa;
                var keypair = rsa.generateKeyPair({bits: 2048, e: 0x10001});
                var ct = keypair.publicKey.encrypt("Arbitrary Message Here");
                var res = keypair.privateKey.decrypt(ct);

                console.info("res=", res);

                var pem = forge.pki.publicKeyToPem(keypair.publicKey);

                console.info("pem=", pem);

                // var xhttp;
                // xhttp=new XMLHttpRequest();
                // xhttp.onreadystatechange = function() {
                //     if (this.readyState == 4 && this.status == 200) {
                //         //cFunction(this);
                //         console.info("coucou");
                //     }
                // };
                // xhttp.open("POST", url, true);
                // xhttp.send();

                var demande = {
                    cle: pem
                };

                $.ajax({
                    url: '/test2',
                    type: 'post',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (data) {
                        //$('#target').html(data.msg);
                        console.info("reponse", data);

                        console.info("res", data.reponse);
                    },
                    data: JSON.stringify(demande)
                });

            }
        }
    });
}

test();
