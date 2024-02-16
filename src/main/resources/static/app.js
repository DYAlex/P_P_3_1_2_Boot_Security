const baseUrl = 'http://localhost:8080/api/v1'
const roleList = []
let selectedUserPassword = ""

// Get roles list
$(document).ready(function () {
    getAllUsers()
    fetch(`${baseUrl}/roles`)
        .then(response => response.json())
        .then(roles => {
            roles.forEach(role => {
                roleList.push(role)
            })
        })
})

// fetch users from api and append to table body
function getAllUsers() {
    const usersTable = $('#users_table-body')
    fetch(`${baseUrl}/users`)
        .then(response => response.json())
        .then(users => {
            usersTable.empty()
            users.forEach(user => {
                const row = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.username}</td>
                        <td>${user.roles.map(role => role.name).join(' ')}</td>
                        <td>
                            <button type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="#edit_user-modal"
                            onclick="showEditModal(${user.id})">Edit</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" data-target="#delete_user-modal"
                            onclick="showDeleteModal(${user.id})">Delete</button>
                        </td>
                    </tr>
                )`
                usersTable.append(row)
            })
        })
        .catch(err => console.error(err))
}

// Add new user
function addNewUser() {
    const newUserForm = $('#new_user-form')[0]
    fillRoles(newUserForm, []);
    newUserForm.addEventListener("submit", (e) => {
        e.preventDefault()
        e.stopPropagation()

        const csrfToken = $(`[name="_csrf"]`, newUserForm).val()

        const newUser = JSON.stringify({
            name: $(`[name="name"]`, newUserForm).val(),
            lastName: $(`[name="lastName"]`, newUserForm).val(),
            age: $(`[name="age"]`, newUserForm).val(),
            username: $(`[name="username"]`, newUserForm).val(),
            password: $(`[name="password"]`, newUserForm).val(),
            roles: Array.from($(`[name="roles"]`, newUserForm)[0].options)
                .filter(o => o.selected)
                .map(o => roleList[o.value - 1])
        })
        fetch(`${baseUrl}/users`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-Token': csrfToken
            },
            body: newUser
        })
            .then(r => {
                if (r.ok) {
                    newUserForm.reset()
                    $('#users_table-tab')[0].click()
                } else {
                    r.json().then(r => {
                        console.error(`Request failed at ${new Date(r.timestamp).toLocaleString()}: ${r.message} `)
                        alert(r.message)
                    })
                }
            })
            .catch(error => {
                console.error(error)
            })
    })
}

// Show edit user modal
function showEditModal(id) {
    const editModal = $('#edit_user-modal')
    const editForm = $('#edit_user-form')[0]
    showModal(editForm, editModal, id)
    editForm.addEventListener('submit', (e) => {
        e.preventDefault()
        e.stopPropagation()
        const csrfToken = $(`[name="_csrf"]`, editForm).val()
        const editedUser = JSON.stringify({
            id: $(`[name="id"]`, editForm).val(),
            name: $(`[name="name"]`, editForm).val(),
            lastName: $(`[name="lastName"]`, editForm).val(),
            age: $(`[name="age"]`, editForm).val(),
            username: $(`[name="username"]`, editForm).val(),
            password: $(`[name="password"]`, editForm).val() ? $(`[name="password"]`, editForm).val() : selectedUserPassword,
            roles: Array.from($(`[name="roles"]`, editForm)[0].options)
                .filter(o => o.selected)
                .map(o => roleList[o.value - 1])
        })
        fetch(`${baseUrl}/users/edit/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-Token': csrfToken
            },
            body: editedUser
        })
            .then(r => {
                if (r.ok) {
                    selectedUserPassword = ""
                    editModal.modal('toggle')
                    $('#users_table-tab')[0].click()
                } else {
                    r.json().then(r => {
                        console.error(`Request failed at ${new Date(r.timestamp).toLocaleString()}: ${r.message} `)
                        alert(r.message)
                    })
                }
            })
            .catch(error => {
                console.error(error)
            })
    })
}

// Delete user Modal
function showDeleteModal(id) {
    const deleteModal = $('#delete_user-modal')
    const deleteForm = $('#delete_user-form')[0]
    showModal(deleteForm, deleteModal, id)
    deleteForm.addEventListener('submit', (e) => {
        e.preventDefault()
        e.stopPropagation()
        const csrfToken = $(`[name="_csrf"]`, deleteForm).val()
        fetch(`${baseUrl}/users/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-Token': csrfToken
            }
        }).then(status)
            .then(r => {
                if (r.ok) {
                    selectedUserPassword = ""
                    deleteModal.modal('toggle')
                    $('#users_table-tab')[0].click()
                } else {
                    r.json().then(r => {
                        console.error(`Request failed at ${new Date(r.timestamp).toLocaleString()}: ${r.message} `)
                        alert(r.message)
                    })
                }
            })
            .catch(function (error) {
                console.error(error)
            })
    })
}

// Helper functions
function showModal(form, modal, id) {
    modal.show()
    requestUserJSONResult(id)
        .then(user => {
            $(`[name="id"]`, form).val(user.id)
            $(`[name="name"]`, form).val(user.name)
            $(`[name="lastName"]`, form).val(user.lastName)
            $(`[name="age"]`, form).val(user.age)
            $(`[name="username"]`, form).val(user.username)
            selectedUserPassword = user.password
            fillRoles(form, user.roles)
        })
        .catch(function (error) {
            error.json().then(r => {
                console.error(`Request failed at ${new Date(r.timestamp).toLocaleString()}: ${r.message} `)
            })
        })
}

function requestUserJSONResult(id) {
    return fetch(`${baseUrl}/users/${id}`)
        .then(status)
        .then(json);
}

function status(response) {
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(response)
    }
}

function json(response) {
    return response.json()
}

function fillRoles(form, userRoles) {
    userRoles = userRoles.map(r => r.id)
    const select = $(`[name="roles"]`, form).attr('size', roleList.length).empty()
    roleList.forEach(role => {
        select.append(userRoles.indexOf(role.id) >= 0
            ? `<option value="${role.id}" selected >
                                 ${role.name}
                            </option>`
            : `<option value="${role.id}" >
                                 ${role.name}
                            </option>`)
    })
}

// Test event of page reloading
window.addEventListener('load', function () {
    console.error(`Window was reloaded at ${new Date().toLocaleString()}`)
})