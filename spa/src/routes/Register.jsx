import React, { useState } from 'react'
import { Redirect, withRouter } from 'react-router-dom'
import { InputText } from 'primereact/inputtext'
import { Button } from 'primereact/button'
import 'primereact/resources/themes/saga-blue/theme.css'
import 'primereact/resources/primereact.min.css'
import 'primeicons/primeicons.css'

const Register = () => {

    const [disabled, setDisabled] = useState(true)
    const [userEmail, setUserEmail] = useState('')
    const [registered, setRegistered] = useState(false)

    function validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        return re.test(String(email).toLowerCase())
    }

    function registerUser() {
        // console.log('Button: ' + userEmail)
        // setRegistered(true);
        fetch('/ded/api/users', {
            method: 'POST',
            mode: 'cors', // no-cors, *cors, same-origin
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            credentials: 'same-origin',
            body: 'email=' + userEmail,
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error('Something went wrong!')
            } else {
                // console.log(document.cookie.split('; ').find(
                //     row => row.startsWith('DED_SESSION')))
                setRegistered(true)
            }
        })
        .catch((err) => {
            console.log(err)
        })
    }

    return !registered ? (
        <div>
            <h1>Register</h1>
            <div className="p-formgroup-inline">
                <label htmlFor="userEmail"
                       className="p-text-normal">Email: </label>
                <InputText id="userEmail" type="email"
                           className="p-inputtext-sm" placeholder="Email"
                           onChange={(e) => {
                               if (validateEmail(e.target.value)) {
                                   setDisabled(false)
                                   setUserEmail(e.target.value)
                               } else {
                                   setDisabled(true)
                               }
                           }} />
                <Button type="button" label="Submit" className="p-button-sm"
                        disabled={disabled} onClick={registerUser} />
            </div>
        </div>
    ) : (<Redirect to="/create-character" />)
}

export default withRouter(Register)