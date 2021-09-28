declare const $: any;
// tslint:disable-next-line: no-string-literal
window['$'] = window['jQuery'] = $;

export class JqueryConfigs {
    constructor() { }

    // MÉTODO DE INICIALIZACIÓN DE DATATABLE ADMILTE
    configDataTable(nombreTabla: string): void {
        $(() => {
            $(`#${nombreTabla}`).DataTable({
                destroy: true,
                responsive: true,
                lengthChange: true,
                autoWidth: false,
                buttons: ['excel'],
                language: {
                    lengthMenu: 'Mostrar _MENU_ registros',
                    paginate: {
                        first: 'Primero',
                        last: 'Último',
                        next: 'Siguiente',
                        previous: 'Anterior'
                    },
                    search: 'Búsqueda:',
                    info: 'Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros'
                }
            }).buttons().container().appendTo('#' + nombreTabla + '_wrapper .col-md-6:eq(0)');
        });
    }

    configModalDataTable(nombreTabla: string): void {
        $(() => {
            $(`#${nombreTabla}`).DataTable({
                destroy: true,
                responsive: false,
                lengthChange: true,
                autoWidth: false,
                buttons: [],
                language: {
                    lengthMenu: 'Mostrar _MENU_ registros',
                    paginate: {
                        first: 'Primero',
                        last: 'Último',
                        next: 'Siguiente',
                        previous: 'Anterior'
                    },
                    search: 'Búsqueda:',
                    info: 'Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros'
                }
            }).buttons().container().appendTo('#' + nombreTabla + '_wrapper .col-md-6:eq(0)');
        });
    }

    // FEES TABLE
    configFeesDataTable(nombreTabla: string): void {
        $(() => {
            $(`#${nombreTabla}`).DataTable({
                destroy: true,
                responsive: false,
                lengthChange: true,
                autoWidth: false,
                buttons: [],
                searching: false,
                language: {
                    lengthMenu: 'Mostrar _MENU_ registros',
                    paginate: {
                        first: 'Primero',
                        last: 'Último',
                        next: 'Siguiente',
                        previous: 'Anterior'
                    },
                    info: 'Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros'
                }
            }).buttons().container().appendTo('#' + nombreTabla + '_wrapper .col-md-6:eq(0)');
        });
    }

    // MÉTODO DE INICIALIZACION DE TOOLTIPS
    configToolTip(): void {
        $(() => {
            $('[data-toggle="tooltip"]').tooltip({
                trigger: 'hover'
            });

            $('[data-toggle="tooltip"]').on('click', () => {
                $(this).tooltip('hide');
            });
        });

    }

    configSelect(): void {
        $(() => {
            // Initialize Select2 Elements
            $('.select2').select2();

            // Initialize Select2 Elements
            $('.select2bs4').select2({
                theme: 'bootstrap4'
            });
        });
    }

    // VALIDATIONS
    configValidation(): void {
        $(() => {
            $.validator.setDefaults({
                submitHandler: () => {
                    alert('Form successful submitted!');
                }
            });
            $('#clientForm').validate({
                rules: {
                    email: {
                        required: true,
                        email: true,
                    },
                    password: {
                        required: true,
                        minlength: 5
                    },
                    terms: {
                        required: true
                    },
                },
                messages: {
                    email: {
                        required: 'Please enter a email address',
                        email: 'Please enter a vaild email address'
                    },
                    password: {
                        required: 'Please provide a password',
                        minlength: 'Your password must be at least 5 characters long'
                    },
                    terms: 'Please accept our terms'
                },
                errorElement: 'span',
                errorPlacement: (error, element) => {
                    error.addClass('invalid-feedback');
                    element.closest('.form-group').append(error);
                },
                highlight: (element, errorClass, validClass) => {
                    $(element).addClass('is-invalid');
                },
                unhighlight: (element, errorClass, validClass) => {
                    $(element).removeClass('is-invalid');
                }
            });
        });
    }
}
