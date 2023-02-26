# RecipeMaker
  ![splash](https://photos.app.goo.gl/TfYtZ2mcJQNzD3Ji8)
## Definición del Proyecto
Recipe maker es una aplicación movil, que busca facilitar la búsqueda de recetas por parte de los usuarios. Mediante el uso de un modelo de detección de objetos, se necesitara únicamente de una foto con los ingredientes que se desea hacer una receta, la aplicación detectará dichos alimentos y buscará recetas que concuerden
## Características del proyecto 
•	Arquitectura MVVM. <br>
•	Clean Architecture <br>
•	Corrutinas <br>
•	Inyección de dependencias con Dagger Hilt <br>
•	Firebase (Athentication, Firestore Database, Storage) <br>
•	LiveData <br>
•	Flows <br>
•	StateFlow <br>
•	Vistas reutilizables con Constraint Layout <br>
•	Diseños y estilos creados para la reutilización por todo el proyecto <br>
## Pantalla de Login y Registro
Los usuarios se podrán registrar por medio de un correo electrónico y contraseña o con su cuenta de Google, y el inicio de sesión de igual manera se lo puede hacer por medio de los dos medios. <br><br>
   ![login](https://lh3.googleusercontent.com/UK_fsNyV35aTVApX-dhiD_FOhylLVtltAE9peek_oJVnqHBOhLLAt7EQSpxxt8WVlxRGJb4cOPDzN0elHxG6moWNNlBUkB5MuMMMZpI1-A_-Re-qAnqrVUAx1UYi8j_oWNuFhGawH4InyBnodSYfZII37qaWPSxoouIuE7kmgMxAzlVH_5zeYUkbcUWhXoUlnnRx0ZtQPC8Sy7G0m5veSzXtUei0UmuEcgb6IYHVb6UghDUyh5KcluiFjATPQM5wYvdZRwtjvbbDVHzjwfRTTSF-kCLulMlJrOIiL3-eOQE8HiS-mU_VD4QrUVXP2vZ5frw3Nnw0zpFmlJx5Qq_9iWKkwieevQ6bl6lGE2nUWnLhLbAyHn4A0WEQEaR0VzMoEQsOpcPoynIY_N3Sj1kK9wbuP5ScCMeW9l6dz5ZFq750ZQvihtsPr_r-o5uY3a7-W7iU2JtmEj6VPnFXe0Jlb2ZIlWgh0j498oZJedJboXbgCWIlvoTyFSQI89MUZa5KGV4uijEEI89jMjUS2PHSs9W3H_Fq7PUAgfuIkJpE9djFTrJ1ADnfgUqvrMJKD-bdtSW953tm95hBRksNj1kX8MMEQHQdF-xuqq-EAtzDgULCf4ClayrV7rd8nsiczR_j6LzFKCZtV01M1OclWrMoeGbEJ62ExLWS4cWXTCxrbQ5lIWCo6zyu8xdtFjW4UDgUOKJ0WRjlX9qBhr-8u4uiF4or1lYCIUotqKPV9rN97deT0tt1b_2M7Ryy9Xp-aCR9KAV8r5UPZVjvyCCowtaInQTESIj18m-1u_bdHQ8umR1XDsq4qFfLqqyiJFGcRDcZ4X78FT750m3_ibmDBDjiSbEhR-GjPOIbJFJQyC9bVM5mjO61t9Z5kL9joHTHh5SxL7fgnQbPFI6Tdol0yKECYH8PqDfQaGwMBf9iMWcWRtha=w279-h619-s-no?authuser=0)
   ![register](https://lh3.googleusercontent.com/aFDkFs6dj30UMZdes6lu2r_p1XSxdd8nESWNtpPXoukE2fobj3gaqfp8ZdWw7cvfOwK5Jm33Y31IfJrAr7GA6_ZiP3ceu3mxanTUPzUXorJa5iMD_5Weu4WJTS3K90_lr2ZFBZrnHG7JCPvF-ZShFnkDmnIiTb0e0jPDfPefVOw3bUdwMTseJXxi87nvMbEfUcPm_SRiR2cJn2ciOJuMhAt34UAOfbSOt-fLBdYb1wtlIDYbli44EAuwpyrK7ecDzLlZUxjuxQaGgyCXx5eDFfXS109vYWvBWyC-hXpQmMPCBbjdG0JvLxsBD7tQvnIzhxconfaMg24u7XtUw5aAPdZKvvqYQ-mtBgIaaVHgkU8FdFbyOIGX02QC16KDiXI05mTHKxB42Pj_vpLTmyOV7zrFlGtAcah3paIGgsP20p8Jln7rP2R4rxNmo_QKjr1l0d6eyROObo35kSr95zKlV50YUkTljxn6QoggkVcTKb9L__yz4Dt8FPIpa84EE-BzLYIw9hbRLRwvezWffwFMG9jcmm_Tx2OXwD3I7AHtePEUI_Ss1qMQfOM_Xgho98wb8a4DkWiwFUDvGzrYDB_6foDi1RBlBPjh7NZHMJRrVv6utz3Rpn7ChvKekAt3ijKGuyHXlK7anS9fPlAVX8dcUipeuAs0oCW3pBh3be6rjbTOyo4eIXOU05HZM2DFZToNN5rx45sa_loegcpaIQGG5CHo11m0Py8lEmo4lUWVphwqLpnjTOiNE8udM5mrIupuNoXLkG81AefEiAoQi_M3zHinuovQCbmiJgy1B98DDy6XXLlDm6FJlX1xJx5enDlMxhN2JtFgT_MbbTE_RknIXXdb2_RLwSg3iPQ37RDu8vTn8gCgOe4gbWpMtC6qRIUX5e6paUhyBF9YNtY_sw91bFnQnzG0Z7a70JcIUz27DXIs=w279-h619-s-no?authuser=0)
  
## Pantalla Perfil
Una vez el usuario hará iniciado sesión, podrá visualizar su perfil en el que se encuentra las recetas que el ha subido, haciendo que la cantidad de recetas vaya creciendo con el tiempo, y una lista de sus recetas que le parecieron interesantes y guardo <br><br>
    ![profile](https://lh3.googleusercontent.com/-jcpcq4nTprP69PSvOZyoQOxoT_6TNk-Reezl5lKka_VmeD4xfj5qCSvG_uu_d76n58a1F1oPpi3CN2atMHkz0fxTVVN4xpT1ItjLw2r3cbhh4dEXkRe2DMNuJF1jP95ChRaHs5mYb-1NsGmXUWCjsr1l0Kt_XMjlBbcqF9CDtZmICsYcP43ZXuLFz3UIe97xYg6PjPw5AqHeQ7rEiJo28cYlLJ484Cc6IQ3BiAU-cPrBPgxQoFxHxJdQDeqGotf5xXkctZNVekHXHSCADC4LMu_8xwgJ278n_IwFEs4tOmAA_m8SowzlSi2f4mciC93HPxcZOtNI-EIRe5vMk_LuLPXnglIfQ2uJ9_4XY3TAjpIIl_XIT7qGun9DICsHYX8iixSQzi1TPCzgB_5MT0Fprk4zYyPID2A1ZBJ7gwy80kFgTh4m-hd3MAL_FDam_qM_ApotYpIg0q7WyrUmYq3r7lDQjyjxMAI_Vo5vanM84kyGPgPSUGpRRfvjsblYUp8dNDJ7p9vDBulWyEYRiqAQN6nvEPPIK8ewavlwuoCh-18YpS0KQDKFucXhtK_Jl4CmzRqKhWJM6KTySMT35dRG9CoHSnmfwLKi_7T8nU3lu7APBL2UqJ-NjXGICSJEsFYTZmStfh1TneJeo3WK_U2Ckn-sjd5I6jRA1GHgq5FB4mXkBmSPk7JBXux7fx5QY9fPfuWia32-xjBlU58zT4K1cY6pWjbPWOCQwuGZucDeKDF3_7i77MZ-2erLt2iO-cUlxerQEqDIaoHXRDv0SS2zJc6-kgkqhsw3hORGF4mJQm-8Z1phGB3cAl2P4jd7X4sgfdDoowpTXZfQmRbraWu_gWiglBgQXTIoYT0_lMlCALGQptTbdxCnzFT3w9P_1b9Lw1u03Z1aG9_DsjHePw2wsBdchThY8i8stTh_CCBoQeb=w279-h619-s-no?authuser=0)
    ![registerrecipe](https://lh3.googleusercontent.com/3TrvyF_aP9ZiF1ncvzSfMZbxTa5NUgxt1bSFjPUq_3YzXNDWDbG8G0OvhU3A1KDenLS0wKXnPAyLOIHg5ZAXMbGsJESG0jJ0S8Ztj-O1RhImpHDgfA-of3FW_OuF8H-2vRT2r0PFzL72blUQABj0U5wWAk7pPicljRvJB5iZ_vkHs45ROCtD0OFZKGQMnd7DnmKUDS9pBg3nBm3x7LI2eOJ3cHX8J5CKoICU4SaraCgFxWyQrIPL6Tkj1OJSjIFw0ryD1diKSs6QoB3Y8lNaJhoANvuBmzVHjj0ZJlIFd0L6N5xci4RGDFdQEfwnjl6kFqlTJ-9WeR7QrEYjf0NbgaUWR0lvZ51QdBC02GwTSZ65X_u5LyopxKNWZmLi3tHZVU6BfGxfh8FhP3NxcF3fZfm7S7KqILn1yniNc3d6oEzpxtjak9VQa-Xkqehr9z_dSEjKZq_F76ot_v-KRNJ0vAuOwsuRrcMw0GaqI_jQ5Yw-QQVJGSGY0OVzl_zawzGVUOZp6DCiFAwC4JB-xkoOW2dqYOFtz0RRgysU7kmccSKy2EKKyh64ZoytTsCdxkQW6ssvwcBWrURYn025FiA7MzJTGVJ1WZelnB4bGcbXsI7TWH5-mDHYshpud12zdlBAqqnbaCFSMy3l6K_Kg95dD_425wNjEL8-i3822GS_aUUogmo3DT9PzU8bdSrdEoLNHL823J44ZHERoofkiO8FekYld-VZodJWFO380wBeKuJQQq5D6RLdOaZGHgszE9MiupGZ2lQcsdsfgxQwqW41G-zxm_PgYLiD4FEPp4iqK92OOSOUAyrgf1rA4Dut8rabUDigf-4ERgeF0mYANbXEZye-hvWsslQWbRKlIib7Kw19b3tTZWWfh15Xk-2CHiQ0JGDFuV4G4VAX5_-NsuEFMAdU79_qH9hRfnSqf21JIxDA=w279-h619-s-no?authuser=0)
    
   
## Busqueda de recetas
El usuario puede explorar las distintas recetas con las que cuenta la aplicación por medio de un buscador tradicional o puede usar la funcionalidad de detección de ingredientes para buscar una receta de una forma más sencilla y rápida <br><br>
    ![searchr](https://lh3.googleusercontent.com/VFUNTXnZpxrsszD_Bkq825rOppX0IborszX7EX1t1PI4qAdl7rQhWGcqU7JQwKfRZfyNdptEb3fN2Ht720wxmjyA9xDtDjz0NYDinYmEcPK0nG7TV3ZfYe7NsLGSQ5ACl1qxphoHMeXy0qX3GbFgaCOKmBcd0VpmV37MR_9-b_BckbrcEki5GlcQ_Va2WuU0PbzlCmrI3uTElHOd7gFw0bHEaA5HIotLHBNnkVSSrC20BNMG3A_QFMJdgzz7S0M67Khzd1oRjcl8yCUp0NeaCy1aoAUWYsMdMQuF1crWEelly5B-keNxZ5hPTdwdO-hQQ9jD1DbyS-SKHxsTdf4VHqhqSNFjC1wk3M64KKDw-1wLMdstIYrx2TIpMzYq9kHlKfgXiGkRFS1sJt-EdaSEGPBABnLmZtRCA8L4q7tKbC5oQyENRYRoHqgtggdOtbsTgwwlwPwMK_oVzhLlg0eNdsDdvaukjeRgs7dlx1bcJcLf4g7ZLQkt_X9V63rDbtUFS9HcR_PGuGR9hI2ikmkW0PxmtKOfTnkbfLDPu14Dj5G7zFUuf0JH3-vCcBxH4-eKaiESUzIAtqAhRTbWP8SWnVUMX2EKN5xu5QEzAnsqjoPAOuuy5wyWstcOWGL6E8Lxpb38RZZxwqUkGlgMyL7c6MIBUS3GNZ68cN-cwcCteWLxtonO3oMp_iTKvGpJYY55914NkoBJTmBHogjiol3YcYbNJunIMqbG9uV5X7Qozw5-0Cx1bLHEoWh1Uxf8qnvBBj9zhnnUjO0rd5dIVJE1grylCkonTy1k_wXxA3onhwid4fETNYKYuesL4HjcJeOfC_4OQq9CBhRbYjNdHEWWWd7FXzxMevsZQcHN6SszSiiSAV7r-4HotOztV0Xrl4YXdlJlvgbzSRdlnqEcR0uzexWL6--ENJI-5aSvyMdJMspw=w279-h619-s-no?authuser=0)
    ![searchi](https://lh3.googleusercontent.com/3uu6LwQfMVYN0NYys1AFWYVYl7oTXhahyejjzLdAJLgYkuTHEONI5w3RCzsPvAqQL8y3ewjMN6O8-Gvn2rGy3uoyyfYnDUr5mlqFz3tFMGswVFaeblZIuQlxRnU_IWluu5kpx0jtjtJ24Nh3ieQpmc8gNECeXjVgAnzYXlj_kVIHTvemlNmD_3QZtP-tinUBK3rFrbHHde0bRig4jd-qk1Bk-nmWk55KX9pELtv_ooOwnljdqssPQgXPsfYmmFdPguE_MAeYiQ3N0hJ8vpbKp289vDUgu5BlfEJRYkQlZrpHn7-FRDshlbzqyEd6vK7bkjKislKufEil7nm-cMEpUSr6sE9tIWhOP8MpzVgd4RGuGwNF4OQVl_x789Enq2i0xrLbVuWspATBienwZ9T33eX5xbTS0NPKBR7VpufFemL4oZx2qw4l3QwcoeBExghDaLh-agd-2O-hSHSON9HpdSXIKa_btTBCDynz6S7YaRaUyylkHBdL89MeS_eyPVzBhPYOitIeAMDZhnaZ_UkvnyLdHe5dto8rMu9g9KnpVEkCZZp2jKHdMhNsr6Utqul_pL_UIdwn1nBBm8MajiJeShVhogDJkGo48T2ZrPYMZTE7ERaAu1zztcaCSD_4CHKIzOeQXaLPp1oAjxrxsw252K6IwGREyh46G4ghEGGDSk2rQqE-LtZwXP1qUMR0fdYWyfg_cfULW4IYMSPccuX2iWoUSHlBhCKhREbXbimNF-z8TztpQ2iK24RDW0HK_YxzcVzdSxVBmyB-Z9XlU4Tner7Bz8W_-cIKXzuVRrUCu8JEmB9MlAWKGOvCr-Wfm8N4QII5DAfm4ReHOPiYfgT-ZkTFhv3mJLrYZWgkjn70WjZ0di17K4Hx024fAiqd1kjvPv63iJkKxD0lsJCfnFMLllkeJHwZvDxy4ANURYf_vhui=w279-h619-s-no?authuser=0)
    
