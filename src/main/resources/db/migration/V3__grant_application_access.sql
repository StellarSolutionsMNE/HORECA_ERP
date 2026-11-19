GRANT USAGE ON SCHEMA public TO stellar_erp_app;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON TABLE public.company,
             public.app_user,
             public.company_membership
    TO stellar_erp_app;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public
    TO stellar_erp_app;